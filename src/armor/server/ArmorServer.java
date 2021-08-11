package armor.server;

import armor.common.ArmorInterface;
import armor.dto.ArmorDTO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AnhTuan
 */
public class ArmorServer extends UnicastRemoteObject implements ArmorInterface {

    private FileReader fr;
    private BufferedReader br;
    private StringTokenizer stk;
    private FileWriter fw;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public ArmorServer() throws RemoteException {
        super();
    }

    @Override
    public boolean createArmor(ArmorDTO dto) throws FileNotFoundException, IOException {
        List<ArmorDTO> list = new ArrayList<>();
        list = findAllArmor();
        list.add(dto);
        if (writeFile(list)) {
            return true;
        }
        return false;
    }

    @Override
    public ArmorDTO findByArmorID(String id) throws FileNotFoundException, IOException {
        ArmorDTO data;
        List<ArmorDTO> list = new ArrayList<>();
        list = findAllArmor();
        for (ArmorDTO armorDTO : list) {
            if(armorDTO.getArmorId().equals(id)){
                 return data = new ArmorDTO(armorDTO.getArmorId(), armorDTO.getClassification(), armorDTO.getDescription(), armorDTO.getStatus(), armorDTO.getTimeOfCreate(), armorDTO.getDefense());
            }
        }
        return null;
    }

    @Override
    public List<ArmorDTO> findAllArmor() throws FileNotFoundException, IOException {
        List<ArmorDTO> list = new ArrayList<>();
        fr = new FileReader("Armor.txt");
        br = new BufferedReader(fr);
        String line = "";
        while ((line = br.readLine()) != null) {
            try {
                stk = new StringTokenizer(line, ";");

                String ArmorId = stk.nextToken().trim();

                String Classification = stk.nextToken().trim();

                String Time = stk.nextToken().trim();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                int Defense = Integer.parseInt(stk.nextToken().trim());

                String Description = stk.nextToken().trim();

                String Status = stk.nextToken().trim();

                ArmorDTO armorDTO = new ArmorDTO(ArmorId, Classification, Description, Status, formatter.parse(Time), Defense);
                list.add(armorDTO);
            } catch (ParseException ex) {
                Logger.getLogger(ArmorServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        br.close();
        fr.close();
        return list;
    }

    @Override
    public boolean removeArmor(String id) throws FileNotFoundException, IOException {
        List<ArmorDTO> list = new ArrayList<>();
        list = findAllArmor();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getArmorId().equals(id)) {
                list.remove(i);
            }
        }
        if (writeFile(list)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateArmor(ArmorDTO dto) throws FileNotFoundException, IOException {
        List<ArmorDTO> list = new ArrayList<>();
        list = findAllArmor();
        for (ArmorDTO armorDTO : list) {
            if (dto.getArmorId().equals(armorDTO.getArmorId())) {
                armorDTO.setClassification(dto.getClassification());
                armorDTO.setDefense(dto.getDefense());
                armorDTO.setDescription(dto.getDescription());
                armorDTO.setStatus(dto.getStatus());
            }

        }
        if (writeFile(list)) {
            return true;
        }

        return false;
    }

    public boolean writeFile(List<ArmorDTO> List) throws IOException {
        fw = new FileWriter("Armor.txt");
        for (ArmorDTO armorDTO : List) {
            String data = armorDTO.getArmorId() + ";"
                    + armorDTO.getClassification() + ";"
                    + formatter.format(armorDTO.getTimeOfCreate()) + ";"
                    + armorDTO.getDefense() + ";"
                    + armorDTO.getDescription() + ";"
                    + armorDTO.getStatus() + "\n";
            fw.write(data);
        }
        fw.close();
        return true;
    }

}
