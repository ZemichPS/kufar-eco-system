package by.zemich.advertisementservice.domain.specification;

public interface Specification<T> {
    /**
     * Проверяет, удовлетворяет ли объект домена данной политике.
     *
     * @param domainObject объект домена, для которого проверяется политика
     * @return true, если политика выполняется, иначе false
     */
    boolean isSatisfiedBy(T domainObject);

    default Specification<T> and(Specification<T> other) {
        return domainObject -> isSatisfiedBy(domainObject) && other.isSatisfiedBy(domainObject);
    }

    default Specification<T> or(Specification<T> other) {
        return domainObject -> isSatisfiedBy(domainObject) || other.isSatisfiedBy(domainObject);
    }

    default Specification<T> not(Specification<T> other) {
        return domainObject -> !other.isSatisfiedBy(domainObject);
    }

}
