package cz.kalas.training.sentencegenerator.domain.word;

import cz.kalas.training.sentencegenerator.model.WordCategory;
import cz.kalas.training.sentencegenerator.model.entity.Word;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents batch of random words of desired size
 */
public class RandomWordLoad implements WordLoad {

    private final int size;

    public RandomWordLoad(int size) {
        this.size = size;
    }

    @Override
    public Word getWord() {
        return null;
    }

    @Override
    public Collection<Word> getWords() {
        int[] randomWordCategoryOrdinals = ThreadLocalRandom.current()
                .ints(size, 0, WordCategory.values().length)
                .toArray();
        return IntStream.range(1, size + 1)
                .boxed()
                .map(i -> createDummyWord(i, WordCategory.values()[randomWordCategoryOrdinals[i - 1]]))
                .collect(Collectors.toList());
    }

    private Word createDummyWord(int seed, WordCategory wordCategory) {
        return new Word(seed + wordCategory.name().toLowerCase(), wordCategory);
    }
}
