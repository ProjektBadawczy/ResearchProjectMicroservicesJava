package studies.research.project.microservicces.GraphService.graphloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import studies.research.project.microservicces.GraphService.model.GraphsContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class GraphLoader {

    private final JSONParser parser = new JSONParser();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<GraphsContainer> loadGraphsFromFile(String fileName) throws IOException {
        try (InputStream is = GraphLoader.class.getResourceAsStream("/" + fileName)) {
            JSONArray jsonArray = (JSONArray) Try.of(() -> new String(is.readAllBytes()))
                    .mapTry(parser::parse)
                    .getOrElseThrow(e -> new RuntimeException("Error during graphs initialization from file %s".formatted(fileName), e));

            List<JSONObject> graphTuples = jsonArray.stream().toList();
            return graphTuples.stream()
                    .map(graphsTuple -> getGraphsContainerFromJsonObject(graphsTuple, fileName))
                    .toList();
        }
    }

    private GraphsContainer getGraphsContainerFromJsonObject(JSONObject graphsTuple, String fileName) {
        return Try.of(() -> objectMapper.readValue(graphsTuple.toJSONString(), GraphsContainer.class))
                .getOrElseThrow(() -> new RuntimeException("Error during graphs initialization from file %s".formatted(fileName)));

    }
}
