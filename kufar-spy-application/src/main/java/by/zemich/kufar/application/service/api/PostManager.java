package by.zemich.kufar.application.service.api;

public interface PostManager<P, S> {
    P create(S source);

}
