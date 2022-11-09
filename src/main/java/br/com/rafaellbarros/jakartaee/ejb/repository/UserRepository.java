package br.com.rafaellbarros.jakartaee.ejb.repository;

import br.com.rafaellbarros.jakartaee.ejb.adapter.UserAdapter;
import br.com.rafaellbarros.jakartaee.ejb.config.UserFederationConfig;
import br.com.rafaellbarros.jakartaee.ejb.core.repository.BaseFederationRepository;
import br.com.rafaellbarros.jakartaee.ejb.helper.CredentialHelper;
import br.com.rafaellbarros.jakartaee.ejb.helper.IdentityHelper;
import br.com.rafaellbarros.jakartaee.ejb.model.adapter.UserAdapterModel;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.annotation.PostConstruct;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class UserRepository extends BaseFederationRepository<User> {

    protected static final Logger logger = Logger.getLogger(UserRepository.class);

    protected ComponentModel model;

    protected KeycloakSession session;

    @PersistenceContext(name = "keycloak-user-storage-jpa")
    protected EntityManager em;

    protected UserFederationConfig config;

    public UserFederationConfig getConfig() {
        return config;
    }

    public void setConfig(UserFederationConfig config) {
        this.config = config;
    }

    @PostConstruct
    protected void init() {
        logger.info("initializing " + UserRepository.class.getSimpleName());
    }

    @Override
    public User getUserById(Long id) {
        logger.debugf("getUserById called with id = {}", id);

        User entity = em.find(User.class, id);

        if (entity == null || entity.getId() == null) {
            logger.debugf("could not find user by id: {}", id);
            return null;
        }

        logger.debugf("Found user id: {}", entity.getId());

        return entity;
    }

    @Override
    public User getUserByUsername(String username) {
        logger.debugf("getUserByUsername called with username = {}", username);

        List<User> result = Collections.EMPTY_LIST;

        try {
            TypedQuery<User> query = em.createNamedQuery("getUserByUsername", User.class);
            query.setParameter("username", username);
            query.setHint("org.hibernate.comment", "search user by username.");

            result = query.getResultList();

            if (result.isEmpty()) {
                logger.debugf("username {} not found", username);
            }
        }catch (Exception e) {
            logger.debugf("getUserByUsername failed with message: {}", e.getMessage());
        }

        return result.isEmpty() ? null : result.get(0);
    }

    // TODO: Refactoring
    @Override
    public User getUserByEmail(final String email) {
        logger.debugf("getUserByEmail called with email = {}", email);
        // TODO: CREATE VALIDATION OR USER BEANVALIDATION IN DTO
        if (this.getConfig() == null) {
            System.out.println("A");
        } else {
            if (this.getConfig().queryUserByEmail() == null) {
                System.out.println("B");
            }
        }
        if (entityManager == null) {
            System.out.println("C");
        }

        TypedQuery<User> query = em.createNamedQuery("getUserByEmail",  User.class);
        query.setParameter("email", email);
        query.setHint("org.hibernate.comment", "search user by email.");

        List<User> result = query.getResultList();

        if (result.isEmpty()) {
            logger.infof("could not find user by email: {}", email);
        }

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public int getUsersCount() {
        logger.debug("getUsersCount called");
        return ((Number) em.createNamedQuery("getUserCount").getSingleResult()).intValue();
    }

    @Override
    public List<UserModel> getUsers(Integer firstResult, Integer maxResults, RealmModel realm) {
        logger.debug("getUsers called");

        TypedQuery<User> query = em.createNamedQuery("getAllUsers", User.class);

        List<User> results = query.getResultList();

        if (firstResult >= 0)
            query.setFirstResult(firstResult);
        if (maxResults >= 1)
            query.setMaxResults(maxResults);

        if (results.isEmpty()) {
            logger.debug("No users found");
            return Collections.EMPTY_LIST;
        }

        List<UserModel> users = new LinkedList<>();
        // TODO: for SSO
        // for (User entity : results) users.add(new UserAdapter(this.session, realm, this.model, entity));

        for (User entity: results) {
            users.add(new UserAdapterModel(entity));
        }


        return users;
    }

    @Override
    public List<UserModel> searchForUserByUsernameOrEmail(String search, Integer firstResult, Integer maxResults, RealmModel realm) {
        logger.debugf("searchForUserByUsernameOrEmail called with search = {}", search);

        TypedQuery<User> query = em.createQuery(this.config.querySearchForUser(), User.class);

        query.setParameter("search", "%" + search.toLowerCase() + "%");

        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }

        List<User> results = query.getResultList();

        if (results.isEmpty()) {
            logger.debug("User not found");
            return Collections.EMPTY_LIST;
        }

        List<UserModel> users = new LinkedList<>();

        for (User entity : results) users.add(new UserAdapter(this.session, realm, this.model, entity));
        return users;
    }

    // https://access.redhat.com/solutions/32314
    // https://stackoverflow.com/questions/2506411/how-to-troubleshoot-ora-02049-and-lock-problems-in-general-with-oracle


    @Transactional
    public UserModel addUser(String username) {
        logger.debugf("addUser() called with username = {}", username);

        if (!IdentityHelper.isValidUsername(em, username)) {
            logger.errorf("Username {} already exists", username);
            throw new RuntimeException("Username already exists.");
        }

        Person p = null;
        User u = null;

        try {
            p = new Person();
            p.setName(username);
            p.setMiddle("-");
            p.setFamily("-");
            p.setIssn("-");
            p.setStatus("1");
            p.setCreation(Date.valueOf(LocalDate.now()));
            em.persist(p);
            em.flush();

           // em.flush();
            // userTransaction.commit();
            // em.getTransaction().commit();

            // User will not have direct access upon registration.
            // An update-password required action will be set for new users.
            u = new User();
            CredentialHelper temporaryCredential = new CredentialHelper.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .usePunctuation(true)
                    .build();

            u.setId(p.getId());
            u.setPerson(p);
            u.setUsername(username);
            u.setPassword(temporaryCredential.generate(20));
            u.setEmail("-");

            em.persist(u);
            em.flush();
           // em.persist(u);
            //em.flush();
            // em.clear();
            // userTransaction.commit();

        } catch (PersistenceException e) {
            logger.errorf("[ERROR] addUser() {} : {}", username, e.getMessage());
            e.printStackTrace();
        }
        // UserAdapter userAdapter = new UserAdapter(this.session, realm, this.model, u);


        UserAdapterModel userAdapterModel = new UserAdapterModel(u);

        logger.debugf("successful added user: {}", username);
        logger.debugf("userAdapterModel: {}", userAdapterModel);
        return userAdapterModel;
    }

    @Override
    public Boolean removeUser(String externalId) {
        logger.debugf("removeUser called with externalId = {}", externalId);

        // EntityManager em = JPAUtil.getEntityManager();

        User entity = getUserByUsername(externalId);
        if (entity == null) return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        logger.infof("successful removed user: {}", entity.getUsername());
        return true;
    }

    @Override
    public Boolean updateUser(User entity) {
        logger.debug("updateUser called");

        // EntityManager em = JPAUtil.getEntityManager();

        if (entity == null) return false;

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        logger.infof("successful updated user: {}", entity.getUsername());
        return true;
    }

    @Override
    public Boolean isValidPassword(String credential) {
        return IdentityHelper.isValidPassword(credential, this.config);
    }

    @Remove
    public void close() {
        logger.info(UserRepository.class.getSimpleName() + " closing...");
    }

}
