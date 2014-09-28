<html>

<body>
  <h1>
        <div align="center"><img src="http://florent.paitrault.free.fr/noel_2014/banners.png"/></div>
        <div align="center">En direct de l&apos;Atelier des Lutins du P&egrave;re No&euml;l</div>
  </h1>
  <div align="center">
        <p style="font-size: 250%;"><img src="http://florent.paitrault.free.fr/noel_2014/ball.jpg"/>
        J -${countdown} avant No&euml;l
        <img src="http://florent.paitrault.free.fr/noel_2014/ball.jpg"/></p>
        <br/><br/>
        Cher lutin ${user},
        <br/><br/>
        Voici un r&eacute;sum&eacute; de l&apos;avanc&eacute;e des pr&eacute;paratifs de l&apos;ensemble des lutins du P&egrave;re No&euml;l
        <br/>
        Pour plus de d&eacute;tail, rendez-vous sur le site : lien
        <br/><br/>
        Les id&eacute;es de cadeaux suivantes ont &eacute;t&eacute; cr&eacute;&eacute;s ou mises &agrave; jour par les lutins :
        <br/>
        <table width="720px">
        <#list wishes as wish>
            <tr>
                <td><div align="center"><h2>${wish.dest}</h2></div></td>
            </tr>
            <tr>
                <td>${wish.descr}</td>
            </tr>
        </#list>
        </table>
  </div>
  <div align="center">
        <br/><br/>
        Si vous ne souhaitez plus recevoir la newsletter merci d'envoyer un email &aacute; l&apos;addresse 
        <a href="mailto:florentpaitrault@yahoo.fr">florentpaitrault@yahoo.fr</a>.
        <br/>
  </div>
</body>
</html>  