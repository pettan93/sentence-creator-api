package cz.kalas.training.sentencegenerator.converter;

import cz.kalas.training.sentencegenerator.exception.SentenceStructureException;
import cz.kalas.training.sentencegenerator.model.WordCategory;
import cz.kalas.training.sentencegenerator.model.dto.SentenceYodaDto;
import cz.kalas.training.sentencegenerator.model.entity.Sentence;
import cz.kalas.training.sentencegenerator.model.entity.Word;
import cz.kalas.training.sentencegenerator.model.entity.WordSentenceUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class GoodDataYodaSentenceToDtoConverter implements Converter<Sentence, SentenceYodaDto> {

    private final Function<Sentence, Boolean> sentenceValidator;

    @Override
    public SentenceYodaDto convert(Sentence source) {
        if (!sentenceValidator.apply(source)) throw new SentenceStructureException("", source);

        var words = source.getUsedWords()
                .stream()
                .map(WordSentenceUsage::getWord)
                .collect(Collectors.toMap(Word::getWordCategory, Function.identity()));

        return new SentenceYodaDto(
                String.format("%s %s %s",
                        words.get(WordCategory.VERB).getText(),
                        words.get(WordCategory.ADJECTIVE).getText(),
                        words.get(WordCategory.NOUN).getText())
        );
    }
}
