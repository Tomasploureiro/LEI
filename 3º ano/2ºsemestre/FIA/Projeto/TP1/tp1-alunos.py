import gymnasium as gym
import numpy as np
import pygame

ENABLE_WIND = False
WIND_POWER = 15.0
TURBULENCE_POWER = 0.0
GRAVITY = -10.0
RENDER_MODE = 'human'
RENDER_MODE = None #seleccione esta opção para não visualizar o ambiente (testes mais rápidos)
EPISODES = 300

env = gym.make("LunarLander-v3", render_mode =RENDER_MODE, 
    continuous=True, gravity=GRAVITY, 
    enable_wind=ENABLE_WIND, wind_power=WIND_POWER, 
    turbulence_power=TURBULENCE_POWER)


def check_successful_landing(observation):
    x = observation[0]
    vy = observation[3]
    theta = observation[4]
    contact_left = observation[6]
    contact_right = observation[7]

    legs_touching = contact_left == 1 and contact_right == 1

    on_landing_pad = abs(x) <= 0.2

    stable_velocity = vy > -0.2
    stable_orientation = abs(theta) < np.deg2rad(20)
    stable = stable_velocity and stable_orientation
 
    if legs_touching and on_landing_pad and stable:
        print("✅ Aterragem bem sucedida!")
        return True

    print("⚠️ Aterragem falhada!")        
    return False
        
def simulate(steps,seed=None, policy = None):    
    observ, _ = env.reset(seed=seed)
    for step in range(steps):
        action = policy(observ)

        observ, _, term, trunc, _ = env.step(action)

        if term or trunc:
            break

    success = check_successful_landing(observ)
    return step, success



#Perceptions
##TODO: Defina as suas perceções aqui

def get_perceptions(observation):

    return {
        "x": observation[0],       # posição horizontal
        "y": observation[1],       # posição vertical
        "vx": observation[2],      # velocidade horizontal
        "vy": observation[3],      # velocidade vertical
        "theta": observation[4],   # orientação (em radianos)
        "vtheta": observation[5],  # velocidade angular
        "left_leg": observation[6],
        "right_leg": observation[7]
    }


#Actions
##TODO: Defina as suas ações aqui
def actions_system(percep):
    # Inicializa a ação: [acelerador do motor principal, controle dos motores laterais]
    main_engine = 0.0
    lateral_engine = 0.0

    # Regra 1: Correção da velocidade vertical
    if percep["vy"] < -0.5 and percep["y"] > 0.0:
        main_engine = min(1.0, -percep["vy"])  # Ajuste progressivo baseado na velocidade de queda

    # Regra 2: Correção de orientação (theta) e velocidade angular (vtheta)
    if abs(percep["theta"]) > 0.05 or abs(percep["vtheta"]) > 0.2:
        if percep["theta"] < -0.1 or percep["vtheta"] < -0.2:
            lateral_engine = -0.55  # Correção forte para a direita
        elif percep["theta"] > 0.1 or percep["vtheta"] > 0.2:
            lateral_engine = 0.55  # Correção forte para a esquerda
        elif percep["theta"] < -0.05:  
            lateral_engine = -0.52  # Microajuste para a direita
        elif percep["theta"] > 0.05:
            lateral_engine = 0.52  # Microajuste para a esquerda

    # Regra 3: Correção da posição horizontal
    # Antes de corrigir x, verificamos se a orientação está OK
    if abs(percep["theta"]) < 0.2:  
        if percep["x"] > 0.1 and percep["vx"] > 0.1:
            lateral_engine = -0.75  # Move para a esquerda
        elif percep["x"] < -0.1 and percep["vx"] < -0.1:
            lateral_engine = 0.75  # Move para a direita

    return np.array([main_engine, lateral_engine])


def reactive_agent(observation):
    ##TODO: Implemente aqui o seu agente reativo
    ##Substitua a linha abaixo pela sua implementação
    percep = get_perceptions(observation)
    action = actions_system(percep)
    return action 
    
    
def keyboard_agent(observation):
    action = [0,0] 
    keys = pygame.key.get_pressed()
    
    print('observação:',observation)

    if keys[pygame.K_UP]:  
        action =+ np.array([1,0])
    if keys[pygame.K_LEFT]:  
        action =+ np.array( [0,-1])
    if keys[pygame.K_RIGHT]: 
        action =+ np.array([0,1])

    return action
    

success = 0.0
steps = 0.0
for i in range(EPISODES):
    st, su = simulate(steps=1000000, policy=reactive_agent)

    if su:
        steps += st
    success += su
    
    if su>0:
        print('Média de passos das aterragens bem sucedidas:', steps/success*100)
    print('Taxa de sucesso:', success/(i+1)*100)
    
