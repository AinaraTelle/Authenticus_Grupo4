package es.deusto.sd.user_interface.service;

import java.time.LocalDateTime;
import java.util.*;

import es.deusto.sd.user_interface.dto.CreateArchivoDTO;
import es.deusto.sd.user_interface.dto.CreateCasoDTO;
import es.deusto.sd.user_interface.dto.TipoAnalisisDTO;
import es.deusto.sd.user_interface.entity.CaseItem;
import es.deusto.sd.user_interface.external.AuthenticusGateway;

public class CaseService {
    private final List<CaseItem> cases = new ArrayList<>();
    private final AuthenticusGateway gateway = new AuthenticusGateway();

    public boolean createCase(String userEmail, String caseNname, List<String> imagePaths, 
        String analysisType, LocalDateTime createdDate) {
        
        ArrayList<CreateArchivoDTO> createArchivosDTO= new ArrayList<>();
        for(String p1:imagePaths){
            String[] partesRuta= p1.split("\\\\");
            String ultRuta = partesRuta[partesRuta.length-1];
            createArchivosDTO.add(new CreateArchivoDTO(ultRuta, p1));
        }
        
        TipoAnalisisDTO tipoAnalisisDTO = this.getTipoAnalisis(analysisType);
        
        CreateCasoDTO createCasoDTO = new CreateCasoDTO(caseNname, tipoAnalisisDTO, 
            createdDate, createArchivosDTO);
        boolean correcto = gateway.createCase(createCasoDTO, userEmail);
            
        return correcto;
    }

    public synchronized List<CaseItem> listCasesForUser(String userEmail) {
        List<CaseItem> res = new ArrayList<>();
        for (CaseItem c: cases) if (c.getUserEmail().equals(userEmail)) res.add(c);
        return res;
    }

    public TipoAnalisisDTO getTipoAnalisis(String analysisType){
        TipoAnalisisDTO tipoAnalisisDTO=null;
        switch (analysisType) {
            case "Type A":
                tipoAnalisisDTO=TipoAnalisisDTO.ALTERAC_CONT;
        
            case "Type B":
                tipoAnalisisDTO=TipoAnalisisDTO.VERACID_CONT;
            
            case "Type C":
                tipoAnalisisDTO=TipoAnalisisDTO.AMBOS;
            default:
                tipoAnalisisDTO=TipoAnalisisDTO.ALTERAC_CONT;
        }
        return tipoAnalisisDTO;
    }

    public  List<String> listLastCasesOfUser(String userEmail, int n) {
        if (n <= 0) return new ArrayList<>();
        
        String token=gateway.getToken(userEmail);
        ArrayList<String> listCases = gateway.getCases_str(userEmail, n, token);
        
        // all.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())); // newest first
        // if (all.size() <= n) return all;
        return new ArrayList<>(listCases.subList(0, n));
    }

    public synchronized List<CaseItem> listCasesForUserBetween(String userEmail, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        List<CaseItem> res = new ArrayList<>();
        if (start == null || end == null) return res;
        for (CaseItem c: cases) {
            if (!c.getUserEmail().equals(userEmail)) continue;
            java.time.LocalDateTime d = c.getCreatedAt();
            if (d == null) continue;
            if ((d.isEqual(start) || d.isAfter(start)) && (d.isEqual(end) || d.isBefore(end))) res.add(c);
        }
        res.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())); // newest first
        return res;
    }

    public synchronized void analyzeCase(CaseItem c) {
        // Simulate analysis work
        List<String> imgs = c.getImagePaths() == null ? new ArrayList<>() : c.getImagePaths();
        for (String img : imgs) {
            // score between 0 and 1
            double score = Math.random();
            c.setImageScore(img, score);
        }
        String r = "Analysis result for '" + c.getName() + "': type=" + c.getAnalysisType()
            + ", images=" + imgs.size();
        c.setResult(r);
    }

    public synchronized boolean deleteCase(CaseItem c) {
        if (c == null) return false;
        return cases.removeIf(existing -> existing.getId() == c.getId());
    }

    public synchronized boolean addImages(CaseItem c, List<String> newPaths) {
        if (c == null || newPaths == null || newPaths.isEmpty()) return false;
        c.addImagePaths(newPaths);
        // Optionally re-run analysis to include the new images
        analyzeCase(c);
        return true;
    }
}