/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package armor.server;

import armor.common.ArmorInterface;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author Modern 15
 */
public class ArmorServerConnect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(11111);
            ArmorInterface armorInterface = new ArmorServer();
            String serviceName = "rmi://localhost:11111/ArmorInterface";
            Naming.rebind(serviceName, armorInterface);
            System.out.println("Sercice " + serviceName + " is bound. ");
        } catch (Exception ex) {
            System.out.println("Trouble " + ex);
        }
    }
    
}
