/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armor.common;

import armor.dto.ArmorDTO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author AnhTuan
 */
public interface ArmorInterface extends Remote {

    boolean createArmor(ArmorDTO dto) throws FileNotFoundException, IOException;

    ArmorDTO findByArmorID(String id) throws FileNotFoundException, IOException;

    List<ArmorDTO> findAllArmor() throws FileNotFoundException, IOException;

    boolean removeArmor(String id) throws FileNotFoundException, IOException;

    boolean updateArmor(ArmorDTO dto) throws FileNotFoundException, IOException;

}
