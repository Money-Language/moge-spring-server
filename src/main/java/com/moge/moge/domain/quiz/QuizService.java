package com.moge.moge.domain.quiz;

import com.moge.moge.domain.quiz.dto.req.PostBoardReq;
import com.moge.moge.domain.quiz.dto.req.PostQuizAnswerReq;
import com.moge.moge.domain.quiz.dto.req.PostQuizReq;
import com.moge.moge.domain.quiz.dto.res.PostBoardRes;
import com.moge.moge.domain.quiz.dto.res.PostQuizAnswerRes;
import com.moge.moge.domain.quiz.dto.res.PostQuizRes;
import com.moge.moge.global.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.moge.moge.global.exception.BaseResponseStatus.DATABASE_ERROR;
import static com.moge.moge.global.exception.BaseResponseStatus.POST_INVALID_QUIZ_ANSWER_HINT;
import static com.moge.moge.global.util.ValidationUtils.checkTitleNull;

@Service
public class QuizService {

    private final QuizDao quizDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public QuizService(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    public PostBoardRes createBoards(int userIdx, PostBoardReq postBoardReq) throws BaseException {
        try {
            // title , categoryIdx null 체크

            return quizDao.createBoard(userIdx, postBoardReq);
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostQuizRes createQuiz(PostQuizReq postQuizReq) throws BaseException {
        try {
            // null 체크

            return quizDao.createQuiz(postQuizReq);
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostQuizAnswerRes createAnswer(PostQuizAnswerReq postQuizAnswerReq) throws BaseException {
        //try {
            // null 체크

            if (postQuizAnswerReq.getHint() != null && !postQuizAnswerReq.getHint().equals("OBJECTIVE") && !postQuizAnswerReq.getHint().equals("SUBJECTIVE")) {
                throw new BaseException(POST_INVALID_QUIZ_ANSWER_HINT);
            }
            if (postQuizAnswerReq.getHint() == "OBJECTIVE") {
                return quizDao.createObjectiveAnswer(postQuizAnswerReq);
            }
            return quizDao.createSubjectiveAnswer(postQuizAnswerReq);
        //} catch(Exception exception) {
        //    throw new BaseException(DATABASE_ERROR);
        //}
    }
}
