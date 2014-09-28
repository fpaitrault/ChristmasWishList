<html>
<style>
    .count_down {
            position: absolute;
            top: 0px;
            left: 50%;
            margin-left: -121px;
    }
    .count_down_container { 
        position: relative;
        height: 170px; 
    }
    .box_inside {
        position: absolute;
        display: inline-block;
        border: 0px;
        -webkit-border-top-left-radius: 15px;
        -webkit-border-top-right-radius: 15px;
        -moz-border-radius-topleft: 15px;
        -moz-border-radius-topright: 15px;
        border-top-left-radius: 15px;
        border-top-right-radius: 15px;
        
        left: 0px;
        top: 0px;
        width: 410px;
        height: 25px;
        padding-top: 5px;
        margin-right: 30px;
        
        background: #ca1d1d;
        font-weight: bold;
        font-size: large;
        color: #ffffff;
        text-align: center;
    }
    .box {
        position: relative;
        display: inline-block;
        vertical-align: top;
        border:solid 1px #FF0000;
        background-color: #ffffff;
        -webkit-border-radius: 15px;
        -moz-border-radius: 15px;
        border-radius: 15px;
        -moz-box-shadow: 5px 5px 20px 0px #cfcfcf;
        -webkit-box-shadow: 5px 5px 20px 0px #cfcfcf;
        -o-box-shadow: 5px 5px 20px 0px #cfcfcf;
        box-shadow: 5px 5px 20px 0px #cfcfcf;
        filter:progid:DXImageTransform.Microsoft.Shadow(color=#cfcfcf, Direction=134, Strength=20);
        
        width: 400px;
        padding-top: 30px;
        padding-left: 5px;
        padding-right: 5px;
        margin: 15px;
        
        color: black;
        font-weight: normal;
        font-size: medium;
        text-align: justify;
    }
    
    .box_table {
        height: 100%;
    }
    
    .box_content {
        
        padding-left: 5px;
    }
    
    .box_para {
        margin-top: 5px;
        margin-bottom: 5px;
    }
    
    .text_bold {
        color: green;
        font-weight: bold;
    }
    .text_underline {
        text-decoration: underline;
    }
    .text_important {
        color: red;
        font-weight: bold;
    }
    .text_cite {
        font-style: italic;
    }
</style>
    <div align="center" style="max-width: 1700px;">
        <div class="count_down_container">
          <img height="125px" src="img/lutin_left.png" />
          <img src="img/countdown/compteur_red.png" />
          <img class="count_down" src="img/countdown/d${countdown_u}.png" />
          <img class="count_down" src="img/countdown/g${countdown_d}.png" />
          <img height="125px" src="img/lutin_right.png" />
		</div>
        <div class="box">
         <div class="box_inside">Identifiant</div>
	         <table class="box_table">
	          <tr>
	           <td><img width="50px" src="img/cadenas3.png"/></td>
	           <td class="box_content">
	               <p class="box_para">
	                La liste est pr&eacute;vue pour que chaque personne s&apos;y connecte avec son identifiant et son mot de passe.
	                <span class="text_bold">Les identifiants ne sont pas gard&eacute;s en m&eacute;moire.</span>
	               </p>
	               <p class="box_para">
	                Cela permet de garantir qu&apos;un petit malin,
	                qui partagerait le m&ecirc;me ordi que son lutin, aille fouiner sur sa liste pour voir la hotte se remplir.
	               </p>
	           </td>
	          </tr>
	         </table>
        </div>
        <div class="box">
            <div class="box_inside">Avoir une id&eacute;e pour une autre personne</div>
            <table class="box_table">
                <tr>
                    <td><img width="50px" src="img/elfe.png"/></td>
                    <td class="box_content">
                         <p class="box_para">
                            Comme on est tous des <span class="text_bold">supers lutins</span>, on peut aussi avoir des supers id&eacute;es. 
                         </p>
                         <p class="box_para">
                            Alors on a le droit d&apos;&eacute;crire dans la liste d&apos;une personne pour inscrire une nouvelle id&eacute;e.
                            Cette nouvelle id&eacute;e trop originale, ne sera <span class="text_bold">vue que par les lutins</span> et pas par la personne concern&eacute;e.
                            Idem pour les commentaires et r&eacute;servations.
                         </p>
                    </td>
                </tr>
            </table>
         </div>
         <div class="box">
            <div class="box_inside">Annuler une r&eacute;servation</div>
            <table class="box_table">
                <tr>
                    <td><img width="50px" src="img/annulation.png"/></td>
                    <td class="box_content">
                        <p class="box_para">
                            Comme il faut &ecirc;tre sage jusqu&apos;&agrave; la fin, parce que <span class="text_cite text_important">&quot;Le P&egrave;re No&euml;l voit tout&quot;</span>, 
                            on peut aussi d&eacute;cider de <span class="text_bold">supprimer une r&eacute;servation d&apos;id&eacute;e</span>. 
                        </p>
                        <p class="box_para">
                            Il suffit de rentrer dans le menu d&eacute;roulant <span class="text_bold">d&apos;effacer son nom</span> et de cliquer sur la <span class="text_bold">coche verte</span>.
                            Il ne restera plus qu&apos;&agrave; &ecirc;tre sage pour qu&apos;un autre lutin r&eacute;serve l&apos;id&eacute;e. 
                        </p>
                    </td>
                </tr>
            </table>
            </div>
        <div class="box">
         <div class="box_inside">Saisir une id&eacute;e dans sa propre liste</div>
             <table class="box_table">
                 <tr>
                     <td><img width="50px" src="img/cadeau2.png"/></td>
                     <td class="box_content">
                         <p class="box_para">
                             Chaque personne <span class="text_bold">a un onglet</span> dans lequel elle peut saisir ses id&eacute;es pour agrandir sa liste.
                         </p>
                         <p class="box_para">
                            Les zones d&apos;&eacute;dition permettent de mettre en forme le texte, avec de la couleur, des images et des liens.
                         </p>
                         <p class="box_para">
                            <span class="text_underline">Petite indication</span> : quand on ajoute une image, on la rajoute en saisissant <span class="text_bold">l&apos;url de la photo</span> trouv&eacute;e sur 
                            internet et ON OUBLIE PAS de saisir la taille en indiquant la hauteur, au <span class="text_bold">maxi 100px...</span> sinon on va se 
                            retrouver avec des posters sur les &eacute;crans !
                         </p>
                         <p class="box_para">
                            Ce qui est bien c&apos;est de s&eacute;lectionner <span class="text_bold">l&apos;alignement de la photo &agrave; gauche</span>, &ccedil;a fera plus propre.
                         </p>
                         <p class="box_para">
                            Quand on a fini de d&eacute;crire son id&eacute;e on clique sur <span class="text_bold">la petite coche verte</span>.
                         </p>
                     </td>
                 </tr>
             </table>
        </div>
        <div class="box">
            <div class="box_inside">Commenter ou r&eacute;server une id&eacute;e</div>
            <table class="box_table">
                <tr>
                    <td><img width="50px" src="img/commentaires.png"/></td>
                    <td class="box_content">
                         <p class="box_para">
                            Quand une personne a saisi son id&eacute;e, tous les lutins la voient apparaitre dans sa liste.
                         </p>
                         <p class="box_para">
                            Tous les lutins peuvent alors commenter l&apos;id&eacute;e (&quot;<span class="text_cite"> ah ouai pas mal j&apos;en ai vu &agrave; tel endroit</span> &quot;),
                            d&eacute;cider de r&eacute;server l&apos;id&eacute;e dans le menu d&eacute;roulant et valider en cliquant sur la coche verte.
                         </p>
                         <p class="box_para">
                            <span class="text_bold">La personne qui a &eacute;mis son id&eacute;e  <span class="text_underline">ne voit pas</span> les commentaires et les r&eacute;servations saisies par les autres lutins.</span>
                            C&apos;est pourquoi tous les commentaires doivent imp&eacute;rativement <span class="text_underline text_important">&ecirc;tre saisis dans la case <span class="text_cite">commentaire</span></span></span> 
                            et <span class="text_important">non dans la case <span class="text_cite">description</span></span>. Parce que si vous &eacute;crivez un truc dans la case <span class="text_cite">description</span>
                            de l&apos;id&eacute;e saisie par Vanessa, et bien Vanessa pourra le voir... ce serait ballot... !
                         </p>
                    </td>
                </tr>
            </table>
            </div>
        <div class="box">
            <div class="box_inside">Dysfonctionnements</div>
            <table class="box_table">
                <tr>
                    <td><img width="50px" src="img/sos.png"/></td>
                    <td class="box_content">
                        <p class="box_para">
                            Bon c&apos;est pas comme si c&apos;&eacute;tait juste une liste de No&euml;l, alors pour le reste, 
                            vous devriez vous en sortir comme des grands lutins.
                        </p>
                        <p class="box_para">
                            Si jamais un lutin s&apos;aper&ccedil;oit d&apos;un dysfonctionnement notoire sur la liste, du type
                            <span class="text_bold text_cite">&quot;je vois des choses que je devrais pas voir&quot;</span>  il appelle de suite le lutin en chef.
                            Le t&eacute;l&eacute;phone est d&eacute;j&agrave; &eacute;quip&eacute; de sa super sonnerie de No&euml;l pour ne pas passer inaper&ccedil;u en r&eacute;union (hihihi).
                        </p>
                        <p class="box_para">
                            On vous rassure on a test&eacute; et retest&eacute; les combinaisons et aucun bug &agrave; noter, 
                            donc normalement la ligne du lutin en chef ne devrait pas sonner.
                        </p>
                        <p class="box_para">
                            <span class="text_bold">En conclusion, c&apos;est une liste au P&egrave;re No&euml;l, plus elle est longue, mieux c&apos;est, alors &quot;A VOS CLAVIERS&quot; !!!</span>
                        </p>
                    </td>
                </tr>
            </table>
         </div>
    </div>
</html>