package org.luvx.kafka.streams.transformer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.Collections;
import java.util.Set;

/**
 * @author: Ren, Xie
 */
public class WordCountTransformerSupplier implements TransformerSupplier<String, String, KeyValue<String, String>> {

    @Override
    public Transformer<String, String, KeyValue<String, String>> get() {
        return new WordCountTransformer();
    }

    public Set<StoreBuilder<?>> stores() {
        return Collections.singleton(Stores.keyValueStoreBuilder(
                Stores.inMemoryKeyValueStore("Counts"),
                Serdes.String(),
                Serdes.Integer()));
    }
}