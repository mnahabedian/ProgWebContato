/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

MaskedInput({
  elm: document.getElementById('data_nasc'),
  format: 'dd/mm/aaaa',
  allowed: '0123456789',
  separator: '/',
  typeon: 'dma'
});
