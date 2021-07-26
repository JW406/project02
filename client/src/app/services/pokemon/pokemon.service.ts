import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PokemonService {
  constructor(private http: HttpClient) { }

  getAllPokemons() {
    return this.http.get('/api/shop/get-all-pokemons').toPromise() as Promise<any[]>
  }

  getCurrentPokeToken() {
    return this.http.get('/api/user/tokens').toPromise() as Promise<{pokeToken: number}>
  }
}
