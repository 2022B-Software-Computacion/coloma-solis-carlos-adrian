import com.toedter.calendar.JDateChooser
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.util.*
import javax.swing.*

class MascotasForm(parent: JFrame, persona: Persona) : JDialog(parent, true) {
    private val dao : MascotaDAO = MascotaDAO(persona.mascotas)

    private val persona : Persona = persona

    private val campoId : JTextField = JTextField()
    private val campoNombre : JTextField = JTextField()
    private val campoFechaNacimiento : JDateChooser = JDateChooser()
    private val campoEsMacho: JCheckBox = JCheckBox()
    private val campoPeso : JTextField = JTextField()

    private val idLabel : JLabel = JLabel("Id")
    private val nombreLabel : JLabel = JLabel("Nombre")
    private val fechaNacimientoLabel = JLabel("Fecha de nacimiento")
    private val estaMachoLabel = JLabel("Es macho")
    private val pesoLabel = JLabel("Peso")

    private val salida: JTextArea = JTextArea(8, 35).also {
        it.lineWrap = true
    }

    private val panelCampos: JPanel
    private val panelBotones: JPanel
    private val panelSalida: JPanel = JPanel(FlowLayout()).also{it.add(JScrollPane(salida))}

    private val botonCreate: JButton = JButton("Crear").also { it.addActionListener {
        val nuevaMascota: Mascota = getMascotaIngresada()
        dao.create(nuevaMascota)
        salida.text = nuevaMascota.toString()

        mostrarMascotas()
    }
    }

    private val botonRead: JButton = JButton("Leer").also{it.addActionListener{
        val id: Int = campoId.text.toInt()
        val mascotaEncontrada : Mascota? = dao.read(id)
        if(mascotaEncontrada != null) {
            campoId.text = mascotaEncontrada.id.toString()
            campoNombre.text = mascotaEncontrada.nombre
            campoFechaNacimiento.date = mascotaEncontrada.fechaNacimiento
            campoEsMacho.isSelected = mascotaEncontrada.esMacho
            campoPeso.text = mascotaEncontrada.peso.toString()
        }
        else
            JOptionPane.showMessageDialog(this, "Mascota no encontrada")
    }}


    private val botonUpdate: JButton = JButton("Actualizar").also{it.addActionListener({
        val nuevaMascota = getMascotaIngresada()
        dao.update(nuevaMascota)
        JOptionPane.showMessageDialog(this, "Mascota actualizada exitosamente")
        mostrarMascotas()
    })}

    private val botonDelete: JButton = JButton("Eliminar").also{it.addActionListener{
        val id : Int = campoId.text.toInt()
        val seElimino : Boolean = dao.delete(id)
        if(seElimino){
            JOptionPane.showMessageDialog(this, "Mascota eliminada exitosamente")
            mostrarMascotas()
        }else
            JOptionPane.showMessageDialog(this, "No se encontro a la mascota que quieres eliminar")
    }}
    init{
        setSize(400, 350)
        setLocationRelativeTo(parent)
        title = "Mascotas"

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
        panelCampos.add(estaMachoLabel)
        panelCampos.add(campoEsMacho)
        panelCampos.add(pesoLabel)
        panelCampos.add(campoPeso)


        panelBotones.add(botonCreate)
        panelBotones.add(botonRead)
        panelBotones.add(botonUpdate)
        panelBotones.add(botonDelete)

        mostrarMascotas()
    }

    private fun mostrarMascotas(){
        salida.text = dao.mascotas.toString()
    }
    private fun getMascotaIngresada(): Mascota{
        val id: Int = campoId.text.toInt()
        val nombre: String = campoNombre.text
        val fechaNacimiento: Date = campoFechaNacimiento.date
        val esMacho: Boolean = campoEsMacho.isSelected
        val peso: Float = campoPeso.text.toFloat()

        val nuevaMascota: Mascota = Mascota(id, nombre, fechaNacimiento, esMacho, peso)
        return nuevaMascota
    }
}