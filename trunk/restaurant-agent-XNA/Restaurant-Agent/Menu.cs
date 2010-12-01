using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Restaurant_Agent
{
    class Menu
    {
        protected Texture2D texture;
        public Vector2 position { get; set; }
        Vector2 menuPos = new Vector2(500.0f, 455.0f);
        public int menuW = 170;
        public string temp;
        public string ingrediente1;
        public string ingrediente2;
        public string ingrediente3;
        public string nombre;
        public Menu nombreMenu;
        public bool nuevoTxt = false;
        public int tiempoDeVida = 3;
        public bool visible = false;
        public int tiempoActual = 0;

        public Menu(Texture2D texture, Menu nombreMenu, string nombre)
        {
            this.nombreMenu = nombreMenu;
            this.texture = texture;
            this.nombre = nombre;
        }
        public void setPosition(Vector2 pos)
        {
            position = pos;
        }
        public Vector2 getPosition()
        {
            return menuPos;
        }
        public Texture2D getImage()
        {
            return texture;
        }
    }
}
