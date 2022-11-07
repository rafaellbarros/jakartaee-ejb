package br.com.rafaellbarros.jakartaee.ejb.core.repository.model.entity;

import java.io.Serializable;

public abstract class BaseEntity<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract T getId();

    @Override
    @SuppressWarnings("PMD.ConfusingTernary")
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof BaseEntity)) {
            return false;
        } else {
            final BaseEntity<?> that = (BaseEntity)o;
            return this.getId() == null ? that.getId() == null : this.getId().equals(that.getId());
        }
    }

    @Override
    public int hashCode() {
        return this.getId() == null ? 0 : this.getId().hashCode();
    }

    @Override
    public String toString() {
        final String entidade = this.getClass().getSimpleName();
        return "Entidade [ " + entidade + " ] {id=" + this.getId() + '}';
    }
}