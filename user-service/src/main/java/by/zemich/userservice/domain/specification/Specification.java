package by.zemich.userservice.domain.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T t);

    default Specification<T> and(Specification<T> other) {
        return object -> this.isSatisfiedBy(object) && other.isSatisfiedBy(object);
    }

    default Specification<T> or(Specification<T> other) {
        return object -> this.isSatisfiedBy(object) || other.isSatisfiedBy(object);

    }

    default Specification<T> not() {
        return object -> !this.isSatisfiedBy(object);
    }
}
