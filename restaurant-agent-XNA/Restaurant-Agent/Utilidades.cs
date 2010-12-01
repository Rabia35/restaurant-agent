using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace Restaurant_Agent
{
    class Utilidades
    {
        private static int[] nuevaPosicionY = { 384, 320, 256, 192, 128, 64, 0 };
        private static Estante[] estantes;
        private static Piso[] pisos;
        private static int estLargo = 3;
        private static int estAlto = 4;
        static int sumaNumEstantes = 0;
        static int sumaNumPiso = 0;
        //static int numPisoTotal = 111;

        public static int TraducePosicionX(int numero)
        {
            return (numero - 1) * 64;
        }

        public static int TraducePosicionY(int y)
        {
            int Y;
            Y = nuevaPosicionY[y - 1];
            return Y;
        }

        private static void DibujaEstantes(int sumaX, int sumaY)
        {
            for (int i = 0; i < estLargo; i++)
            {
                for (int j = 0; j < estAlto; j++)
                {
                    estantes[sumaNumEstantes].setPosition(new Vector2(i * Estante.ESTANTEWIDTH + sumaX, j * Estante.ESTANTEHEIGHT + sumaY));
                    sumaNumEstantes++;
                }
            }

        }

        public static Estante[] DibujaEstantes(Estante[] estantesArrive)
        {
            estantes = estantesArrive;
            DibujaEstantes(0, 192);
            DibujaEstantes(384, 192);
            DibujaEstantes(768, 192);
            DibujaEstantes(1152, 192);
            return estantes;

        }

        public static Piso[] DibujaPiso(Piso[] pisosArrive)
        {
            pisos = pisosArrive;

            for (int i = 0; i < 17; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    pisos[sumaNumPiso].setPosition(new Vector2(i * Piso.PISOWH + 64, j * Piso.PISOWH));
                    sumaNumPiso++;
                }
            }

            DibujaPisoPasillo(64, 192,pisos);
            DibujaPisoPasillo(448, 192,pisos);
            DibujaPisoPasillo(832, 192,pisos);
            return pisos;
        }

        private static void DibujaPisoPasillo(int sumX, int sumY, Piso[] pisos)
        {
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    pisos[sumaNumPiso].setPosition(new Vector2(i * Piso.PISOWH + sumX, j * Piso.PISOWH + sumY));
                    sumaNumPiso++;
                }
            }
        }
    }
}
