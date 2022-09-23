package org.vinogradov.myclient.clientLogic;

public interface MyTripleConsumer <T, V, U>{

    void accept(T t, V v, U u);
}
