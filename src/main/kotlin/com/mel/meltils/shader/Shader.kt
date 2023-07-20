package com.mel.meltils.shader

import com.mel.meltils.util.ColorGradient
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20.*
import java.awt.Color

object Shader {
    private val initialized = System.currentTimeMillis()
    private var fs: Int = 0
    private var vs: Int = 0
    private var program: Int = 0
    var colors = mutableListOf<Color>(
        Color(191, 255, 50),
        Color.GREEN,
        Color.CYAN
    )

    var gradient = ColorGradient(colors)
    private var fragmentShader = """
        #version 120
        
        uniform int time;
        ${getChromaArray(gradient)}
        uniform sampler2D outTexture;

        varying vec2 outTextureCoords;
        varying vec4 outColor;
        
        
        void main() {
        vec4 originalColor = texture2D(outTexture, outTextureCoords) * outColor;
        
        int index = int(mod(gl_FragCoord.x + gl_FragCoord.y, 99.0));
        vec3 color = colors[index];
        gl_FragColor = vec4(color.x, color.y, color.z, originalColor.a);
        }
    
    """.trimIndent()
    private var vertexShader = """
        #version 120

        varying vec2 outTextureCoords;
        varying vec4 outColor;

        void main() {
        gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
        outColor = gl_Color;
        outTextureCoords = gl_MultiTexCoord0.st;
        }
    
    """.trimIndent()


    init {
        createShader(vertexShader, fragmentShader)
    }

    fun createShader(vertexShader: String, fragmentShader: String) {
        program = glCreateProgram()
        vs = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vs, vertexShader)
        glCompileShader(vs)
        fs = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fs, fragmentShader)
        glCompileShader(fs)

        glAttachShader(program, vs)
        glAttachShader(program, fs)
        glLinkProgram(program)
        glValidateProgram(program)
    }

    fun bind() {
        glUseProgram(program)
        setUniformInt("time", (System.currentTimeMillis() - initialized).toInt())

    }

    fun unbind() {
        glUseProgram(0)
    }

    fun getChromaArray(gradient: ColorGradient): String {
        var returnString = "vec3 colors[100] = vec3[100] ("
        val offset = (gradient.colors.size * gradient.timePerColor) / 100
        for (i in 0..99) {
            val color = gradient.get(i * offset)
            returnString += "vec3(${color.red / 255f}, ${color.green / 255f}, ${color.blue / 255f}),"
        }
        returnString = returnString.trimEnd(',')
        returnString += ");"
        return returnString
    }

    fun setUniformInt(name: String, value: Int) {
        val location = glGetUniformLocation(program, name)
        if (location != -1) {
            glUniform1i(location, value)
        }
    }
}