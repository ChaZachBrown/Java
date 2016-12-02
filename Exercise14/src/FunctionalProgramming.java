import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class FunctionalProgramming {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("movies.txt");
		
// prints out all movies that grossed over 500 million
		Files.lines(path)
			.filter((row) -> getRevenue(row) > 500.0)
			.sorted() 
			.forEach((line) -> System.out.println(line));

		System.out.println("");

//prints top growsing film of all time
		System.out.println("Top grossing movie ");
		Files.lines(path)
			.sorted((f2, f1) -> Double.compare(getRevenue(f1), getRevenue(f2)))			
			.limit(1)
			.forEach((line) -> System.out.println(line));

//movies made more in the 70's or 80's
		System.out.println("Decade that made most money");
		Map<Integer, Double> decadeRevenue = Files.lines(path)
				.collect(groupingBy(FunctionalProgramming::getDecade, 
						summingDouble(FunctionalProgramming::getRevenue)));
		String topGross =  (decadeRevenue.get(1970) > decadeRevenue.get(1980) ? "70's" : "80's");
		System.out.println("Decade with most revenue between 70s and 80s is: " + String.valueOf(topGross));

	}
	private static Double getRevenue(String s) {
		return Double.parseDouble(s.split("[|]")[2]);
	} 
	private static String getYear(String s) {
		return s.split("[|]")[3];
	} 
	private static Integer getDecade(String s) {
		String decade = getYear(s).substring(0, 3);
		return Integer.parseInt(decade) * 10;
	}
}
	 