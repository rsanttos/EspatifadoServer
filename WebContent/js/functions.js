$(document).ready(function() {
	
	$('.oculto').hide();

	/*======================    Gerenciar Tabela    ====================*/
	
	$('table[role=grid]').addClass("table table-striped gerenciar-tabela dataTable no-footer");
    $('table.gerenciar-tabela').DataTable({
        "language": {
            "url": "/keys/js/Portuguese-Brasil.json"
        }
    });
    /*  Ajustes para tabela do PrimeFaces  */
	$('.ajustar-tabela table[role=grid] tr').removeClass("ui-datatable-odd");
	$('.ajustar-tabela table[role=grid] tr').removeClass("ui-datatable-even");
	
	/*==========================================*/
	
	$(".btn-salvar-empresa").click(function(){
		$(".form-socio").remove();
	});
	
	$(".inv-top-mobile").click(function(e) {
		e.preventDefault();
		jQuery(".inv-navegacao").toggleClass("show-navegacao");
	});

	$("a.inv-lista-menu").click(function(e) {
		if (jQuery(this).hasClass("inv-submenu")) {
			jQuery(this).find('span.glyphicon').toggleClass("active-icon");
		}
	});

	/*===================   Confirmação de senha   =======================*/
	var senha = 0;
  	 $(".senha2").focusout(function(){
  		 if($(this).val() != $('.newsenha').val()){
  			 alert('A nova senha precisa ser igual nos dois campos');
  			 if(senha == 0){
  				 senha = 1;
  				 $(".senha2").focus();
  			 }
  		 }
  	});
  	  	 
});


