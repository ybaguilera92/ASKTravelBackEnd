package cu.sitrans.asktravel.factory;


import cu.sitrans.asktravel.models.types.QuestionListTypes;
import cu.sitrans.asktravel.service.impl.strategies.*;
import cu.sitrans.asktravel.service.strategies.QuestionGetStrategy;
import org.springframework.stereotype.Component;
import java.util.EnumMap;
import java.util.Map;

@Component
public class QuestionListStrategyFactory {

    RecentQuestionsStrategy recentQuestionsStrategy;
    MostAnsweredStrategy mostAnsweredStrategy;
    MostVotedStrategy mostVotedStrategy;
    MostVisitedStrategy mostVisitedStrategy;
    QuestionsByStatusStrategy questionsByStatusStrategy;
    QuestionsByTagStrategy questionsByTagStrategy;
    QuestionsByPhraseStrategy questionsByPhraseStrategy;
    QuestionsByTagStatusStrategy questionsByTagStatusStrategy;
    AllQuestionsStrategy allQuestionsStrategy;
    QuestionsByUserStrategy questionsByUserStrategy;
    PendingQuestionsByUserStrategy pendingQuestionsByUserStrategy;

    private Map<QuestionListTypes, QuestionGetStrategy> strategies = new EnumMap<>(QuestionListTypes.class);

    public QuestionListStrategyFactory(RecentQuestionsStrategy recentQuestionsStrategy,
                                       MostAnsweredStrategy mostAnsweredStrategy,
                                       MostVotedStrategy mostVotedStrategy,
                                       MostVisitedStrategy mostVisitedStrategy,
                                       QuestionsByStatusStrategy questionsByStatusStrategy,
                                       QuestionsByTagStrategy questionsByTagStrategy,
                                       QuestionsByPhraseStrategy questionsByPhraseStrategy,
                                       QuestionsByTagStatusStrategy questionsByTagStatusStrategy,
                                       AllQuestionsStrategy allQuestionsStrategy,
                                       PendingQuestionsByUserStrategy pendingQuestionsByUserStrategy,
                                       QuestionsByUserStrategy questionsByUserStrategy) {

        this.recentQuestionsStrategy = recentQuestionsStrategy;
        this.mostAnsweredStrategy = mostAnsweredStrategy;
        this.mostVotedStrategy = mostVotedStrategy;
        this.mostVisitedStrategy = mostVisitedStrategy;
        this.questionsByStatusStrategy = questionsByStatusStrategy;
        this.questionsByTagStrategy = questionsByTagStrategy;
        this.questionsByPhraseStrategy = questionsByPhraseStrategy;
        this.questionsByTagStatusStrategy = questionsByTagStatusStrategy;
        this.allQuestionsStrategy = allQuestionsStrategy;
        this.questionsByUserStrategy = questionsByUserStrategy;
        this.pendingQuestionsByUserStrategy = pendingQuestionsByUserStrategy;
        initStrategies();
    }

    public QuestionGetStrategy getStrategy(QuestionListTypes type) {
        if (type == null || !strategies.containsKey(type)) {
            throw new IllegalArgumentException("Invalid " + type);
        }
        return strategies.get(type);
    }

    private void initStrategies() {
        strategies.put(QuestionListTypes.RECENT_QUESTIONS, recentQuestionsStrategy);
        strategies.put(QuestionListTypes.MOST_ANSWERED, mostAnsweredStrategy);
        strategies.put(QuestionListTypes.MOST_VISITED, mostVisitedStrategy);
        strategies.put(QuestionListTypes.MOST_VOTED, mostVotedStrategy);
        strategies.put(QuestionListTypes.BY_STATUS, questionsByStatusStrategy);
        strategies.put(QuestionListTypes.BY_TAG, questionsByTagStrategy);
        strategies.put(QuestionListTypes.BY_PHRASE, questionsByPhraseStrategy);
        strategies.put(QuestionListTypes.BY_TAG_STATUS, questionsByTagStatusStrategy);
        strategies.put(QuestionListTypes.PROFILE_QUESTION, questionsByUserStrategy);
        strategies.put(QuestionListTypes.PROFILE_PENDING_QUESTION, pendingQuestionsByUserStrategy);
        strategies.put(QuestionListTypes.ALL, allQuestionsStrategy);
    }
}
