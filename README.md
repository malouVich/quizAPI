# Hvem vil være millionær

Spilkoncept:
 "Hvem vil være millionær?" er en quiz, hvor deltagerne skal besvare en række multiple-choice-spørgsmål korrekt for at avancere til næste niveau. Der er i alt 15 spørgsmål, og hvert spørgsmål har en bestemt pengeværdi. Der er ingen tidsbegrænsning for at svare.

API-endpoints (REST)
Spørgsmål (Questions)
Opret spørgsmål (Create)
POST /api/questions

Hent alle spørgsmål (Read)
GET /api/questions

Hent et bestemt spørgsmål (Read)
GET /api/questions/{id}

Opdater et spørgsmål (Update)
PUT /api/questions/{id}

Slet et spørgsmål (Delete)
DELETE /api/questions/{id}

Spil-session (Game)
Start et nyt spil
POST /api/game/start

Svare på et spørgsmål
POST /api/game/answer

Hent spilstatus (præmie, nuværende spørgsmål, brugte livliner osv.)
GET /api/game/status

Afslut spillet
POST /api/game/end
