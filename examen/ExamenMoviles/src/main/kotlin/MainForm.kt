import com.toedter.calendar.JCalendar
import com.toedter.calendar.JDateChooser
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.ScrollPane
import java.util.*
import javax.swing.BoxLayout
import javax.swing.*
import javax.swing.border.Border

class MainForm: JFrame("CRUD") {

    private val campoId : JTextField = JTextField()
    private val campoNombre : JTextField = JTextField()
    private val campoFechaNacimiento : JDateChooser = JDateChooser()
    private val campoEstaCasado : JCheckBox = JCheckBox()
    private val campoPeso : JTextField = JTextField()

    private val idLabel : JLabel = JLabel("Id")
    private val nombreLabel : JLabel = JLabel("Nombre")
    private val fechaNacimientoLabel = JLabel("Fecha de nacimiento")
    private val estaCasadoLabel = JLabel("Casado")
    private val pesoLabel = JLabel("Peso")

    private val salida: JTextArea = JTextArea(8, 35).also {
        it.lineWrap = true
    }

    private val panelCampos: JPanel
    private val panelBotones: JPanel
    private val panelSalida: JPanel = JPanel(FlowLayout()).also{it.add(JScrollPane(salida))}

    private val botonCreate: JButton = JButton("Crear").also { it.addActionListener {
        val nuevaPersona: Persona = getPersonaIngresada()
        PersonaDAO.create(nuevaPersona)
        salida.text = nuevaPersona.toString()

        mostrarPersonas()
    }
    }

    private val botonRead: JButton = JButton("Leer").also{it.addActionListener{
        val id: Int = campoId.text.toInt()
        val personaEncontrada : Persona? = PersonaDAO.read(id)
        if(personaEncontrada != null) {
            campoId.text = personaEncontrada.id.toString()
            campoNombre.text = personaEncontrada.nombre
            campoFechaNacimiento.date = personaEncontrada.fechaNacimiento
            campoEstaCasado.isSelected = personaEncontrada.estaCasado
            campoPeso.text = personaEncontrada.peso.toString()
        }
        else
            JOptionPane.showMessageDialog(this, "Persona no encontrada")
    }}


    private val botonUpdate: JButton = JButton("Actualizar").also{it.addActionListener({
        val nuevaPersona = getPersonaIngresada()
        PersonaDAO.update(nuevaPersona)
        JOptionPane.showMessageDialog(this, "Persona actualizada exitosamente")
        mostrarPersonas()
    })}

    private val botonDelete: JButton = JButton("Eliminar").also{it.addActionListener{
        val id : Int = campoId.text.toInt()
        val seElimino : Boolean = PersonaDAO.delete(id)
        if(seElimino){
            JOptionPane.showMessageDialog(this, "Persona eliminada exitosamente")
            mostrarPersonas()
        }else
            JOptionPane.showMessageDialog(this, "No se encontro a la persona que quieres eliminar")
    }}

    private val botonMascotas: JButton = JButton("Mascotas").also{it.addActionListener{
        val idPersona = campoId.text.toInt()
        val persona = PersonaDAO.read(idPersona)
        if(persona != null){
            val mascotasDialog = MascotasForm(this, persona)
            mascotasDialog.isVisible = true
        }else
            JOptionPane.showMessageDialog(this, "Persona no encontrada")

    }}
    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 350)
        setLocationRelativeTo(null)
        this.layout = BorderLayout(3, 3)
        panelCampos = JPanel(GridLayout(5, 2))
        this.add(panelCampos, BorderLayout.NORTH)
        panelBotones = JPanel(FlowLayout())
        this.add(panelBotones, BorderLayout.CENTER)
        this.add(panelSalida, BorderLayout.SOUTH)

        panelCampos.add(idLabel)
        panelCampos.add(campoId)
        panelCampos.add(nombreLabel)
        panelCampos.add(campoNombre)
        panelCampos.add(fechaNacimientoLabel)
        panelCampos.add(campoFechaNacimiento)
        panelCampos.add(estaCasadoLabel)
        panelCampos.add(campoEstaCasado)
        panelCampos.add(pesoLabel)
        panelCampos.add(campoPeso)


        panelBotones.add(botonCreate)
        panelBotones.add(botonRead)
        panelBotones.add(botonUpdate)
        panelBotones.add(botonDelete)
        panelBotones.add(botonMascotas)

        mostrarPersonas()
    }

    private fun mostrarPersonas(){
        salida.text = PersonaDAO.personas.toString()
    }
    private fun getPersonaIngresada(): Persona{
        val id: Int = campoId.text.toInt()
        val nombre: String = campoNombre.text
        val fechaNacimiento: Date = campoFechaNacimiento.date
        val estaCasado: Boolean = campoEstaCasado.isSelected
        val peso: Float = campoPeso.text.toFloat()

        val nuevaPersona: Persona = Persona(id, nombre, fechaNacimiento, estaCasado, peso)
        return nuevaPersona
    }

}

public fun iniciarGUI(){
    val frame = MainForm()
    frame.isVisible = true
}