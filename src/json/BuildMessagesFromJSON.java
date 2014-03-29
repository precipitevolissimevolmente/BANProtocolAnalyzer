package json;


import idealisedprotocol.IdealisedMessage;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BuildMessagesFromJSON {
    private String jsonFileRelativePath;

    public String getJsonFileRelativePath() {
        return jsonFileRelativePath;
    }

    public void setJsonFileRelativePath(String jsonFileRelativePath) {
        this.jsonFileRelativePath = jsonFileRelativePath;
    }

    public List<IdealisedMessage> build(String jsonFile) throws IOException {
        InputStream is =
                BuildMessagesFromJSON.class.getResourceAsStream(jsonFile);
        String jsonTxt = IOUtils.toString(is);

        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonTxt);
        return null;
    }

}
