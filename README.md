TPO Algoritmos II / Programacion II
Sistema de Operaciones para Micros


Integrante:
    Juan Desimone - Legajo 1196491 (trabajo individual)

TDAs utilizados y por que:

    Grafo - GraphADTStatic (estatica, matriz de adyacencia)
        Donde: vive en SistemaOperaciones (grafoTerminales) y lo consulta tambien
            AnalisisConexiones.
        Por que: las terminales y sus conexiones forman una digrafo (terminal =
            vertice, ruta = arista). La matriz indica directamente si existe una ruta de X a Y.
            Es estatica porque la cantidad de terminales del escenario se conoce y no crece en ejecucion.
        Uso: addVertx (en agregarTerminal), addEdge (en agregarRuta) y existsEdge (en
            crearViaje, dfsRecursivo, terminalesDesconectadas y los reportes de conexiones).
        Todas las aristas tienen peso 1.

    Diccionario simple - SimpleDictionaryADTDynamic (dinamica)
        Donde: SistemaOperaciones, en cinco instancias (codigoAIndice, indiceATerminal,
            viajes, micros, usosPorRuta). Lo consultan GestionFlota y SimulacionReportes.
        Por que: necesito acceso por clave. Micros y viajes se buscan por su id; las
            terminales se traducen entre su codigo y el indice que ocupan en el grafo; y
            usosPorRuta lleva el contador de viajes de cada ruta. El diccionario da ese
            acceso directo por clave sin recorrer todo. Es dinamico porque se dan de alta
            micros, viajes y rutas durante la ejecucion.
        Uso: add, get y getKeys().exist (altas y consultas en casi todos los modulos).

    Conjunto - SetADTDynamic (dinamica)
        Donde: SistemaOperaciones (el conjunto visitados del DFS) y los recorridos de
            claves (getKeys()) de GestionFlota y SimulacionReportes.
        Por que: en el DFS de rutas posibles necesito marcar que terminales ya visite para
            no repetirlas y poder deshacer el camino (backtracking); el conjunto da
            add/remove/exist sin duplicados. Ademas sirve para recorrer las claves de un
            diccionario de a una.
        Uso: add / remove / exist (en dfsRecursivo); choose / remove / isEmpty (en los
            recorridos de claves).

    Lista - LinkedListADTDynamic (dinamica)
        Donde: resultados de casi todos los modulos.
        Por que: junto resultados cuya cantidad no se sabe de antemano: los caminos de una
            ruta, las terminales que empatan en un maximo, las rutas mas y menos usadas. La
            lista crece segun haga falta.
        Uso: add y get; por ejemplo en rutasPosibles guarda cada camino y la lista completa
            de caminos.

    Cola de prioridad - PriorityQueueADTDynamic (dinamica)
        Donde: PriorityViajes (campo cola).
        Por que: el modulo de prioridad siempre debe atender primero el viaje mas urgente;
            la cola de prioridad devuelve ese elemento sin tener que ordenar a mano. A mayor
            numero, mas prioridad; los empates salen por orden de llegada (FIFO).
        Uso: add, getElement, remove y getPriority (encolar, ver el proximo, atenderlo y
            modificar la prioridad; modificar vacia y reconstruye la cola).
