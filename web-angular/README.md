L'application PWA fonctionne si les entête HTTP paramétrés sur le serveur sont worker-src *; default-src * 'unsafe-inline' 'unsafe-eval'; script-src * 'unsafe-inline' 'unsafe-eval'; connect-src * 'unsafe-inline'; img-src * data: blob: 'unsafe-inline'; frame-src *; style-src * 'unsafe-inline';

/!\ Attention, ces entêtes sont trop permissifs et doivent être retravaillés