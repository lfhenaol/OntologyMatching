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
public class Technical {
    private Resource format;
    private Resource size;
    private Resource location;
    private Requirement requirement;
    private Resource installationRemarks;
    private Resource otherPlatformRequirements;
    private Resource duration;
    private OntModel technicalModel;

    Technical(JsonNode jsonTechnical){
        this.technicalModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        this.format = this.undefinedToResource(jsonTechnical.getNode("format").getText());
        this.size = this.undefinedToResource(jsonTechnical.getNode("size").getText());
        this.location = this.undefinedToResource(jsonTechnical.getNode("location").getText());
        this.installationRemarks = this.undefinedToResource(jsonTechnical.getNode("installationRemarks").getText());
        this.otherPlatformRequirements = this.undefinedToResource(jsonTechnical.getNode("otherPlatformRequirements").getText());
        this.duration = this.undefinedToResource(jsonTechnical.getNode("duration").getText());

        this.requirement = new Requirement(jsonTechnical.getNode("requirement"));

    }

    public Map match(Ontology onto) {
        ArrayList<Resource> localProp = new ArrayList<>();
        localProp.add(this.format);
        localProp.add(this.size);
        localProp.add(this.location);
        localProp.add(this.installationRemarks);
        localProp.add(this.otherPlatformRequirements);
        localProp.add(this.duration);

        ArrayList<String> jresponse = new ArrayList<>();
        jresponse.add("format");
        jresponse.add("size");
        jresponse.add("location");
        jresponse.add("installationRemarks");
        jresponse.add("otherPlatformRequirements");
        jresponse.add("duration");

        String json = "{\n";
        Map similar = onto.getSimilarity(localProp);
        for (int i = 0; i<localProp.size();i++) {
            Resource rs = (Resource) similar.get(localProp.get(i));
            Resource lc = localProp.get(i);
            this.technicalModel.createObjectProperty(rs.toString());
            this.technicalModel.createObjectProperty(lc.toString());
            this.technicalModel.createSymmetricProperty(lc.toString());
            this.technicalModel.add(lc, OWL.equivalentProperty, rs);
            json = json + "\""+jresponse.get(i)+"\": \"" + ((lc.getLocalName().equals("undefined"))? "Unselected":((rs.getLocalName().equals("No-Matches"))? "No matches": rs.toString()))+"\"";
            if(i < (localProp.size()-1)){
                json = json + ",";
            }
        }

        Map result = this.requirement.match(onto);
        this.technicalModel.union(this.requirement.getRequirementModel());
        json = json +",\"requirement\":" +result.get("jsonObject").toString() + "}";

        Map<String,Object> response = new HashMap<>();
        response.put("model",this.technicalModel);
        response.put("jsonObject",json);
        return response;
    }

    private Resource undefinedToResource(String localProperty){
        String uri = "http://www.example.com/undefined";
        return (localProperty.equals("undefined"))? this.technicalModel.createResource(uri) : this.technicalModel.createResource(localProperty);
    }

    public Resource getFormat() {
        return format;
    }

    public void setFormat(Resource format) {
        this.format = format;
    }

    public Resource getSize() {
        return size;
    }

    public void setSize(Resource size) {
        this.size = size;
    }

    public Resource getLocation() {
        return location;
    }

    public void setLocation(Resource location) {
        this.location = location;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Resource getInstallationRemarks() {
        return installationRemarks;
    }

    public void setInstallationRemarks(Resource installationRemarks) {
        this.installationRemarks = installationRemarks;
    }

    public Resource getOtherPlatformRequirements() {
        return otherPlatformRequirements;
    }

    public void setOtherPlatformRequirements(Resource otherPlatformRequirements) {
        this.otherPlatformRequirements = otherPlatformRequirements;
    }

    public Resource getDuration() {
        return duration;
    }

    public void setDuration(Resource duration) {
        this.duration = duration;
    }

    public OntModel getTechnicalModel() {
        return technicalModel;
    }

    public void setTechnicalModel(OntModel technical) {
        this.technicalModel = technical;
    }
}
