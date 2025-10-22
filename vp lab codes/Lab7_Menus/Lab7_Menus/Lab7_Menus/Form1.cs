using System;
using System.Drawing;
using System.Windows.Forms;

namespace Lab7_Menus
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            displayLabel.ForeColor = Color.Black;
            displayLabel.Font = new Font("Times New Roman", 14, FontStyle.Regular);
        }

        // ===== COLOR MENU =====
        private void ClearColor()
        {
            blackToolStripMenuItem.Checked = false;
            blueToolStripMenuItem.Checked = false;
            redToolStripMenuItem.Checked = false;
            greenToolStripMenuItem.Checked = false;
        }

        private void blackToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearColor();
            displayLabel.ForeColor = Color.Black;
            blackToolStripMenuItem.Checked = true;
        }

        private void blueToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearColor();
            displayLabel.ForeColor = Color.Blue;
            blueToolStripMenuItem.Checked = true;
        }

        private void redToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearColor();
            displayLabel.ForeColor = Color.Red;
            redToolStripMenuItem.Checked = true;
        }

        private void greenToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearColor();
            displayLabel.ForeColor = Color.Green;
            greenToolStripMenuItem.Checked = true;
        }

        // ===== FONT MENU =====
        private void ClearFont()
        {
            timesToolStripMenuItem.Checked = false;
            courierToolStripMenuItem.Checked = false;
            comicToolStripMenuItem.Checked = false;
        }

        private void timesToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearFont();
            displayLabel.Font = new Font("Times New Roman", displayLabel.Font.Size, displayLabel.Font.Style);
            timesToolStripMenuItem.Checked = true;
        }

        private void courierToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearFont();
            displayLabel.Font = new Font("Courier New", displayLabel.Font.Size, displayLabel.Font.Style);
            courierToolStripMenuItem.Checked = true;
        }

        private void comicToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearFont();
            displayLabel.Font = new Font("Comic Sans MS", displayLabel.Font.Size, displayLabel.Font.Style);
            comicToolStripMenuItem.Checked = true;
        }

        // ===== STYLE MENU =====
        private void ToggleFontStyle(FontStyle style, ToolStripMenuItem menuItem)
        {
            FontStyle newStyle = displayLabel.Font.Style ^ style;
            displayLabel.Font = new Font(displayLabel.Font.FontFamily, displayLabel.Font.Size, newStyle);

            if (menuItem != null)
                menuItem.Checked = (displayLabel.Font.Style & style) == style;
        }

        private void boldToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ToggleFontStyle(FontStyle.Bold, boldToolStripMenuItem);
        }

        private void italicToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ToggleFontStyle(FontStyle.Italic, italicToolStripMenuItem);
        }

        private void underlineToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ToggleFontStyle(FontStyle.Underline, underlineToolStripMenuItem);
        }

        // ===== CONTEXT MENU =====
        private void ctxBold_Click(object sender, EventArgs e)
        {
            ToggleFontStyle(FontStyle.Bold, null);
            boldToolStripMenuItem.Checked = (displayLabel.Font.Style & FontStyle.Bold) == FontStyle.Bold;
        }

        private void ctxItalic_Click(object sender, EventArgs e)
        {
            ToggleFontStyle(FontStyle.Italic, null);
            italicToolStripMenuItem.Checked = (displayLabel.Font.Style & FontStyle.Italic) == FontStyle.Italic;
        }

        private void ctxUnderline_Click(object sender, EventArgs e)
        {
            ToggleFontStyle(FontStyle.Underline, null);
            underlineToolStripMenuItem.Checked = (displayLabel.Font.Style & FontStyle.Underline) == FontStyle.Underline;
        }

        private void displayLabel_Click(object sender, EventArgs e)
        {

        }
    }
}
