package kate.spring.conversion;

public interface Converter<Source,Result> {
    Result convert(Source source);
}
