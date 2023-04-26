package cu.sitrans.asktravel.factory;

import cu.sitrans.asktravel.models.types.AnswerListTypes;
import cu.sitrans.asktravel.service.impl.strategies.AllAnswersStrategy;
import cu.sitrans.asktravel.service.impl.strategies.AnswersByUserStrategy;
import cu.sitrans.asktravel.service.impl.strategies.BestAnswersByUserStrategy;
import cu.sitrans.asktravel.service.impl.strategies.MostVotedAnswersStrategy;
import cu.sitrans.asktravel.service.strategies.AnswerGetStrategy;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class AnswerListStrategyFactory {

    AllAnswersStrategy allAnswersStrategy;
    MostVotedAnswersStrategy mostVotedAnswersStrategy;
    AnswersByUserStrategy answersByUserStrategy;
    BestAnswersByUserStrategy bestAnswersByUserStrategy;

    private Map<AnswerListTypes, AnswerGetStrategy> strategies = new EnumMap<>(AnswerListTypes.class);

    public AnswerListStrategyFactory(AllAnswersStrategy allAnswersStrategy,
                                     MostVotedAnswersStrategy mostVotedAnswersStrategy,
                                     AnswersByUserStrategy answersByUserStrategy,
                                     BestAnswersByUserStrategy bestAnswersByUserStrategy) {

        this.allAnswersStrategy = allAnswersStrategy;
        this.mostVotedAnswersStrategy = mostVotedAnswersStrategy;
        this.answersByUserStrategy = answersByUserStrategy;
        this.bestAnswersByUserStrategy = bestAnswersByUserStrategy;
        initStrategies();
    }

    public AnswerGetStrategy getStrategy(AnswerListTypes type) {
        if (type == null || !strategies.containsKey(type)) {
            throw new IllegalArgumentException("Invalid " + type);
        }
        return strategies.get(type);
    }

    private void initStrategies() {
        strategies.put(AnswerListTypes.MOST_VOTED, mostVotedAnswersStrategy);
        strategies.put(AnswerListTypes.PROFILE_ANSWERS, answersByUserStrategy);
        strategies.put(AnswerListTypes.PROFILE_BEST_ANSWERS, bestAnswersByUserStrategy);
        strategies.put(AnswerListTypes.ALL, allAnswersStrategy);
    }
}
