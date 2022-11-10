package br.com.rafaellbarros.jakartaee.ejb.repository;

import br.com.rafaellbarros.jakartaee.ejb.adapter.UserAdapter;
import br.com.rafaellbarros.jakartaee.ejb.config.UserFederationConfig;
import br.com.rafaellbarros.jakartaee.ejb.core.repository.BaseFederationRepository;
import br.com.rafaellbarros.jakartaee.ejb.helper.IdentityHelper;
import br.com.rafaellbarros.jakartaee.ejb.model.adapter.UserAdapterModel;
import br.com.rafaellbarros.jakartaee.ejb.model.builder.create.PersonCreateBuilder;
import br.com.rafaellbarros.jakartaee.ejb.model.builder.create.UserCreateBuilder;
import br.com.rafaellbarros.jakartaee.ejb.model.builder.update.UserUpdateBuilder;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.Person;
import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserRepository extends BaseFederationRepository<User> {

    protected static final Logger logger = Logger.getLogger(UserRepository.class);

    protected ComponentModel model;

    protected KeycloakSession session;

    @PersistenceContext(name = "keycloak-user-storage-jpa")
    protected EntityManager em;

    @EJB
    private PersonCreateBuilder personCreateBuilder;

    @EJB
    private UserCreateBuilder userCreateBuilder;

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
        logger.info("getUserById() called with id: " + id);

        User entity = em.find(User.class, id);

        if (entity == null || entity.getId() == null) {
            logger.info("could not find user by id:" + id);
            return null;
        }

        logger.info("Found user id: " + entity.getId());

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
        logger.info("getUsers called");
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

        // TODO: Uncomment for SSO
        // getUsersModel(realm, results);
        return getUserAdapterModelsApi(results);
    }

    @Override
    public List<UserModel> searchForUserByUsernameOrEmail(String search, Integer firstResult, Integer maxResults, RealmModel realm) {
        logger.info("searchForUserByUsernameOrEmail called with search: " + search);

        TypedQuery<User> query = em.createNamedQuery("searchForUser", User.class);
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

        // TODO: Uncomment for SSO
        // getUsersModel(realm, results);
        return getUserAdapterModelsApi(results);
    }

    // https://access.redhat.com/solutions/32314
    // https://stackoverflow.com/questions/2506411/how-to-troubleshoot-ora-02049-and-lock-problems-in-general-with-oracle
    @Transactional
    public UserModel addUser(final String username) {
        try {
            logger.info("addUser() called with username: " + username);
            isValidUsername(username);
            final Person person = persistPerson(username);
            final User user = persistUser(person);

            // TODO: for SSO
            // UserAdapter userAdapter = new UserAdapter(this.session, realm, this.model, u);
            UserAdapterModel userAdapterModel = new UserAdapterModel(user);
            logger.info("addUser() successful User: " + user);
            logger.info("addUser() userAdapterModel: " + userAdapterModel);
            return userAdapterModel;
        } catch (PersistenceException e) {
            logger.errorf("[ERROR] addUser() {} : {}", username, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean updateUser(final User entity) {
        logger.info("updateUser() called: " + entity);
        if (entity == null) return false;
        em.merge(entity);
        logger.info("updateUser() successful updated user: " + entity);
        return true;
    }

    @Override
    public Boolean removeUser(String externalId) {
        logger.debugf("removeUser called with externalId = {}", externalId);

        User entity = getUserByUsername(externalId);
        if (entity == null) return false;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();

        logger.infof("successful removed user: {}", entity.getUsername());
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

    private static List<UserModel> getUserAdapterModelsApi(List<User> results) {
        return results.stream().map(UserAdapterModel::new)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private List<UserModel> getUsersModel(RealmModel realm, List<User> results) {
        return results.stream().map(entity -> new UserAdapter(this.session, realm, this.model, entity))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private User persistUser(Person person) {
        final User user = userCreateBuilder.build(person);
        em.persist(user);
        em.flush();
        return user;
    }

    private Person persistPerson(String username) {
        final Person person = personCreateBuilder.build(username);
        em.persist(person);
        em.flush();
        return person;
    }

    private void isValidUsername(String username) {
        if (!IdentityHelper.isValidUsername(em, username)) {
            logger.errorf("Username {} already exists", username);
            throw new RuntimeException("Username already exists.");
        }
    }
}
