package br.com.rafaellbarros.jakartaee.ejb.model.adapter;

import br.com.rafaellbarros.jakartaee.ejb.model.entity.User;
import org.keycloak.models.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class UserAdapterModel implements UserModel {

    public UserAdapterModel() { }

    public UserAdapterModel(User user) {
        this.entity = user;
    }

    private User entity;

    @Override
    public String getId() {
        return this.entity.getId().toString();
    }

    @Override
    public String getUsername() {
        return this.entity.getUsername();
    }

    @Override
    public void setUsername(String username) {
        this.entity.setUsername(username);
    }

    @Override
    public Long getCreatedTimestamp() {
        return null;
    }

    @Override
    public void setCreatedTimestamp(Long aLong) {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public void setSingleAttribute(String s, String s1) {

    }

    @Override
    public void setAttribute(String s, List<String> list) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public String getFirstAttribute(String s) {
        return null;
    }

    @Override
    public List<String> getAttribute(String s) {
        return null;
    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        return UserModel.super.getAttributeStream(name);
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return null;
    }

    @Override
    public Set<String> getRequiredActions() {
        return null;
    }

    @Override
    public Stream<String> getRequiredActionsStream() {
        return UserModel.super.getRequiredActionsStream();
    }

    @Override
    public void addRequiredAction(String s) {

    }

    @Override
    public void removeRequiredAction(String s) {

    }

    @Override
    public void addRequiredAction(RequiredAction action) {
        UserModel.super.addRequiredAction(action);
    }

    @Override
    public void removeRequiredAction(RequiredAction action) {
        UserModel.super.removeRequiredAction(action);
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String s) {

    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String s) {

    }

    @Override
    public String getEmail() {
        return this.entity.getEmail();
    }

    @Override
    public void setEmail(String email) {
        this.entity.setPassword(email);
    }

    @Override
    public boolean isEmailVerified() {
        return false;
    }

    @Override
    public void setEmailVerified(boolean b) {

    }

    @Override
    public Set<GroupModel> getGroups() {
        return null;
    }

    @Override
    public Stream<GroupModel> getGroupsStream() {
        return UserModel.super.getGroupsStream();
    }

    @Override
    public Set<GroupModel> getGroups(int first, int max) {
        return UserModel.super.getGroups(first, max);
    }

    @Override
    public Set<GroupModel> getGroups(String search, int first, int max) {
        return UserModel.super.getGroups(search, first, max);
    }

    @Override
    public Stream<GroupModel> getGroupsStream(String search, Integer first, Integer max) {
        return UserModel.super.getGroupsStream(search, first, max);
    }

    @Override
    public long getGroupsCount() {
        return UserModel.super.getGroupsCount();
    }

    @Override
    public long getGroupsCountByNameContaining(String search) {
        return UserModel.super.getGroupsCountByNameContaining(search);
    }

    @Override
    public void joinGroup(GroupModel groupModel) {

    }

    @Override
    public void leaveGroup(GroupModel groupModel) {

    }

    @Override
    public boolean isMemberOf(GroupModel groupModel) {
        return false;
    }

    @Override
    public String getFederationLink() {
        return null;
    }

    @Override
    public void setFederationLink(String s) {

    }

    @Override
    public String getServiceAccountClientLink() {
        return null;
    }

    @Override
    public void setServiceAccountClientLink(String s) {

    }

    @Override
    public SubjectCredentialManager credentialManager() {
        return null;
    }

    @Override
    public Set<RoleModel> getRealmRoleMappings() {
        return null;
    }

    @Override
    public Stream<RoleModel> getRealmRoleMappingsStream() {
        return UserModel.super.getRealmRoleMappingsStream();
    }

    @Override
    public Set<RoleModel> getClientRoleMappings(ClientModel clientModel) {
        return null;
    }

    @Override
    public Stream<RoleModel> getClientRoleMappingsStream(ClientModel app) {
        return UserModel.super.getClientRoleMappingsStream(app);
    }

    @Override
    public boolean hasDirectRole(RoleModel role) {
        return UserModel.super.hasDirectRole(role);
    }

    @Override
    public boolean hasRole(RoleModel roleModel) {
        return false;
    }

    @Override
    public void grantRole(RoleModel roleModel) {

    }

    @Override
    public Set<RoleModel> getRoleMappings() {
        return null;
    }

    @Override
    public Stream<RoleModel> getRoleMappingsStream() {
        return UserModel.super.getRoleMappingsStream();
    }

    @Override
    public void deleteRoleMapping(RoleModel roleModel) {

    }
}
