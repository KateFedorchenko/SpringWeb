package kate.spring;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.beans.ConstructorProperties;

@AllArgsConstructor
public class MyData {
    private int num;
    private String str;
    private int[] nums;
    private long l;

//    @JsonCreator
//    public MyData(@JsonProperty("num") int num,
//                  @JsonProperty("str") String str,
//                  @JsonProperty("arrayOfNumbers") int[] nums,
//                  @JsonProperty("l") long l) {
//        this.num = num;
//        this.str = str;
//        this.nums = nums;
//        this.l = l;
//    }

    public int getNum() {
        return num;
    }

    public String getStr() {
        return str;
    }

    @JsonIgnore
    public long getL() {
        return l;
    }

    @JsonProperty("arrayOfNumbers")
    public int[] getNums() {
        return nums;
    }

    public String getFoo() {
        return "foo";
    }

    @JsonProperty("bar")
    public String bar() {
        return "bar";
    }
}
