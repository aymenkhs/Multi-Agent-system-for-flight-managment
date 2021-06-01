package part_2.agents;

import jade.core.Agent;
import org.json.JSONObject;
import part_2.behaviours.Agence_Behaviour;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Agence_agent extends Agent {


    private String base_folder = System.getProperty("user.dir") + "\\src\\part_2\\bases";

    protected void setup(){

        Object[] args = this.getArguments();

        String base_file_name = (String) args[0];

        JSONObject base = load_base(base_file_name);
        addBehaviour(new Agence_Behaviour(base));


    }

    private JSONObject load_base(String base_file_name){

        try {

            for(String file : Objects.requireNonNull(new File(base_folder).list())){

                Path path = Paths.get(base_folder + "//" + file);

                if(file.equals(base_file_name)){

                    String content = Files.readString(path, StandardCharsets.UTF_8);
                    return new JSONObject(content);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
