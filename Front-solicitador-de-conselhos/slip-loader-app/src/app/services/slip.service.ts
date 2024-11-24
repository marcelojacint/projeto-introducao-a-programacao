import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SlipService {
  private apiUrl = 'http://localhost:8080/api/slips'; // Atualize com a URL correta

  constructor(private http: HttpClient) {}

  /**
   * Método para carregar uma quantidade específica de slips.
   * @param quantidade Número de conselhos.
   * @returns Observable de uma lista de slips.
   */
  carregaSlips(quantidade: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/carregarSlips/${quantidade}`);
  }

  /**
   * Método para obter todos os slips.
   * @returns Observable de uma lista de slips.
   */
  getTodosSlips(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
