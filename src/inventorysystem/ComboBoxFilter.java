/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

/**
 *
 * @author user
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ComboBoxFilter extends KeyAdapter
{
    @SuppressWarnings("rawtypes")
    JComboBox cbListener;
    @SuppressWarnings("rawtypes")
    Vector vector;

    @SuppressWarnings("rawtypes")
    public ComboBoxFilter(JComboBox cbListenerParam, Vector vectorParam)
    {
            cbListener = cbListenerParam;
            vector = vectorParam;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void keytyped(String Text)
    {
        Vector v = new Vector();
        // TODO Auto-generated method stub
        String text = Text;
        v = getFilteredList(text.toLowerCase());
        cbListener.setModel(new DefaultComboBoxModel(v));
        cbListener.setSelectedIndex(-1);
        ((JTextField)cbListener.getEditor().getEditorComponent()).setText(text);
        try {
            cbListener.showPopup();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in Combobox filter "+e.getMessage());
        }
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Vector getFilteredList(String text)
    {
            Vector t = new Vector();
            Vector v = new Vector();
            for(int i=0; i<vector.size();i++)
            {
                t.add(vector.get(i).toString().toLowerCase());
            }
            for(int a = 0;a<vector.size();a++)
            {
                if(t.get(a).toString().startsWith(text))
                {
                        v.add(vector.get(a).toString());
                }
            }
            return v;
    }
}