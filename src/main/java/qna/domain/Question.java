package qna.domain;

import qna.CannotDeleteException;
import qna.NotHaveDeleteOwnerShipException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Embedded
    private Answers answers = new Answers();

    private boolean deleted = false;

    public Question() {
    }

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Question(long id, String title, String contents) {
        super(id);
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Question setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public User getWriter() {
        return writer;
    }

    public Question writeBy(User loginUser) {
        this.writer = loginUser;
        return this;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    private boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public int getAnswersSize() {
        return answers.countAnswers();
    }

    public List<DeleteHistory> delete(User loginUser) throws CannotDeleteException {
        checkOwner(loginUser);
        this.deleted = true;

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory.Builder()
                                             .contentType(ContentType.QUESTION)
                                             .contentId(getId())
                                             .deletedBy(writer)
                                             .createDate(LocalDateTime.now())
                                             .build());
        answers.delete(loginUser, deleteHistories);
        return deleteHistories;
    }

    private void checkOwner(User loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new NotHaveDeleteOwnerShipException();
        }
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}