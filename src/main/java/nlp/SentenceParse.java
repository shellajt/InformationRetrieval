package nlp;

/**
 * Created by Jesse Shellabarger on 5/17/2017.
 */
public class SentenceParse {

    public String subject;
    public String verb;
    public String object;

    @Override
    public String toString() {
        return "SentenceParse{" +
                "subject='" + subject + '\'' +
                ", verb='" + verb + '\'' +
                ", object='" + object + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SentenceParse that = (SentenceParse) o;

        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;
        if (verb != null ? !verb.equals(that.verb) : that.verb != null) return false;
        return object != null ? object.equals(that.object) : that.object == null;
    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (verb != null ? verb.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
        return result;
    }
}
