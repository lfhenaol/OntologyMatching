package LOM;

import Ontology.Ontology;
import argo.jdom.JsonNode;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucho on 18/04/2016.
 */
public class OrComposite {
    private Resource type;
    private Resource name;
    private Resource minimumVersion;
    private Resource maximumVersion;
    private OntModel orCompositeModel;

    OrComposite(JsonNode jsonOrComposite){
        this.orCompositeModel = ModelFactory.createOntologyModel();
        this.name = this.undefinedToResource(jsonOrComposite.getNode("name").getText());
        this.type = this.undefinedToResource(jsonOrComposite.getNode("type").getText());
        this.minimumVersion = this.undefinedToResource(jsonOrComposite.getNode("minimumVersion").getText());
        this.maximumVersion = this.undefinedToResource(jsonOrComposite.getNode("maximumVersion").getText());
    }

    public Map match(Ontology onto){
        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.type);
        localProp.add(this.name);
        localProp.add(this.minimumVersion);
        localProp.add(this.maximumVersion);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("type");
        jresponse.add("name");
        jresponse.add("minimumVersion");
        jresponse.add("maximumVersion");

        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.orCompositeModel.createObjectProperty(rs.toString());
            this.orCompositeModel.createObjectProperty(lc.toString());
            this.orCompositeModel.createSymmetricProperty(lc.toString());
            this.orCompositeModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }
        json = json + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.orCompositeModel);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.orCompositeModel.createResource(uri) : this.orCompositeModel.createResource(localProperty);
    }

    public Resource getType() {
        return type;
    }

    public void setType(Resource type) {
        this.type = type;
    }

    public Resource getName() {
        return name;
    }

    public void setName(Resource name) {
        this.name = name;
    }

    public Resource getMinimumVersion() {
        return minimumVersion;
    }

    public void setMinimumVersion(Resource minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public Resource getMaximumVersion() {
        return maximumVersion;
    }

    public void setMaximumVersion(Resource maximumVersion) {
        this.maximumVersion = maximumVersion;
    }

    public OntModel getOrCompositeModel() {
        return orCompositeModel;
    }

    public void setOrCompositeModel(OntModel orComposite) {
        orCompositeModel = orComposite;
    }
}
