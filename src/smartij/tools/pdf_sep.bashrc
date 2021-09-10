#!/bin/bash


#séparation/convertion des pages du fichier pdf (en .txt)
sepnconv(){
    #on enregistre le nombre de page
    a=$(exiftool $1 | grep 'Page Count' | cut -c35-)
    for(( i=1 ; i<=$a ; i++ )){
        b=$(($i-1))
        name="page$b.pdf"
        #on sépare 
        pdftk $1 cat $i-$i output $name
        #on déplace les fichiers pdf dans "/page"
        mv "$name" "$PWD/page/$name"
        #convertir .pdf en .txt 
        nametxt="page$b.txt"
        pdftotext -layout "page/$name" $nametxt
        #on déplace les fichier dans "/page/pagetxt"
        mv "$PWD/$nametxt" "$PWD/page/pagetxt/$nametxt"
    }
}

#Creation du repertoire 
cr_rep(){
if [ ! -e page ]
then
    mkdir page
    cd page
    if [ ! -e pagetxt ]
        then
            mkdir pagetxt
            cd ..
        fi
fi
}

#Verification qu'il n'y a q'un fichier initiale
count(){
    a=0;
    for f in `ls`
    do
        name="$f";
        #Si on trouve un fichier .pdf 
        #on incrémente
        if [[ ${name#*.} == pdf ]]
        then
            filename="$name"
            let "a += 1"    
        fi
        
    done
    #on vérifie la valeur de a
    #si a = 0
    if [ $a -eq 0 ]
    then
        echo "Erreur : Aucun fichier pdf trouvé"
    fi 
    if [ $a -gt 1 ]
    then
        echo "Erreur : Plusieurs fichiers pdf trouvé (il en faut 1 seul)"
    fi
    if [ $a -eq 1 ]
    then
        sepnconv $filename
    fi

}

echo "traitement en cours ..."
cr_rep
count 

echo "Fin de traitement."


