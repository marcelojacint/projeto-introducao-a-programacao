import { Component } from '@angular/core';
import { SlipService } from '../../services/slip.service';  // Ajuste o caminho se necessário

@Component({
  selector: 'app-slip-loader',
  templateUrl: './slip-loader.component.html',
  styleUrls: ['./slip-loader.component.scss'],
})
export class SlipLoaderComponent {
  quantidade: number = 1;  // A quantidade de conselhos que o usuário quer carregar
  slips: any[] = [];  // Lista de conselhos carregados
  loading: boolean = false;  // Controle de carregamento

  constructor(private slipService: SlipService) {}

  /**
   * Carregar conselhos com base na quantidade informada
   */
  carregarSlips() {
    this.loading = true;  // Ativa o estado de carregamento
    this.slipService.carregaSlips(this.quantidade).subscribe(
      (data: any[]) => {
        // Adiciona os novos conselhos à lista existente
        this.slips = data;
        this.loading = false;  // Desativa o estado de carregamento
      },
      (error: any) => {
        console.error('Erro ao carregar conselhos:', error);
        this.loading = false;  // Desativa o estado de carregamento em caso de erro
      }
    );
  }

  /**
   * Carregar todos os conselhos disponíveis
   */
  carregarTodosSlips() {
    this.loading = true;  // Ativa o estado de carregamento
    this.slipService.getTodosSlips().subscribe(
      (data: any[]) => {
        // Substitui todos os conselhos carregados pela lista completa
        this.slips = data;
        this.loading = false;  // Desativa o estado de carregamento
      },
      (error: any) => {
        console.error('Erro ao carregar todos os slips:', error);
        this.loading = false;  // Desativa o estado de carregamento em caso de erro
      }
    );
  }
}
