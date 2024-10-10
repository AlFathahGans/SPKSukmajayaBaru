package spksukmajayabaru;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MultilineHeaderRenderer extends DefaultTableCellRenderer {

    public MultilineHeaderRenderer() {
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.TOP); // Ensure vertical alignment is at the top
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Use HTML to format text as multiline and move text closer to the top
        String headerText = "<html><div style='text-align: center; padding: 2px;'>" + value.toString() + "</div></html>";
        label.setText(headerText);
        return label;
    }

}
