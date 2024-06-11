package bankingapplication;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@EnableScheduling
@SpringBootApplication
public class BankingApplication {

//  public static void main(String[] args) {
//    SpringApplication.run(BankingApplication.class, args);
//  }

  public static void main(String[] args) {

    List<String> strings = new ArrayList<>();
    strings.add("a");
    strings.add("b");
    strings.add("a");
    strings.add("ab");
    Stream<String> sorted = strings.stream().sorted();
    int  intA[] = {1,2,3};
    IntStream stream = Arrays.stream(intA);
    stream.map(e->e+10).filter(e->e%2==0).forEach(e->System.out.println(e));
    System.out.println(strings);
  }

}
