import { Component, OnInit } from '@angular/core';
import { PokemonService } from 'src/app/services/pokemon/pokemon.service';

export interface ItemModel {
  pokeName: string;
  price: number;
  img: string;
}

export interface Tile {
  color: string;
  cols: number;
  rows: number;
  data: ItemModel;
}

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss'],
})
export class ShopComponent implements OnInit {
  tiles: Tile[] = [];
  isWait = false;
  private colors = ['lightblue', 'lightgreen', 'lightpink', '#DDBDF1'];
  constructor(private pokemonService: PokemonService) {}

  async ngOnInit() {
    this.isWait = true;
    const res = await this.pokemonService.getAllPokemons();
    this.tiles = res.map((d) => ({
      color: this.colors[Math.floor(Math.random() * this.colors.length)],
      cols: 1,
      rows: 5,
      data: d,
    }));
    this.isWait = false;
  }
}
