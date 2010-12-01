using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Restaurant_Agent
{
    class Chef
    {
        protected Texture2D texture;

        public Texture2D globo;

        public Vector2 position { get; set; }

        public  int chefH = 105;
        public  int chefW = 71;
        Vector2 chefPos = new Vector2(200.0f, 455.0f);
        Vector2 globoPos = new Vector2(75.0f, 460.0f);
        public string nombre;
        public string temp;
        public string ingrediente1;
        public string ingrediente2;
        public string ingrediente3;
        public string ingrediente4;

        public bool nuevoTxt = false;
        public int tiempoDeVida = 3;
        public bool visible = false;
        public int tiempoActual = 0;


        public Chef(Texture2D theTexture, Texture2D globo, string nombre)
        {
                texture = theTexture;
                this.nombre = nombre;
                this.globo = globo;
        }

        public void setPosition(Vector2 pos) 
        {
            position = pos;
        }
        public Vector2 getPosition() 
        {
            return chefPos;
        }
        public Texture2D getImage()
        {
            return texture;
        }
    }
}
