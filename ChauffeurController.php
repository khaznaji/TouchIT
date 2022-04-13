<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Chauffeur;
use App\Form\ChauffeurType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

class ChauffeurController extends AbstractController
{
    /**
     * @Route("/chauffeur", name="app_chauffeur")
     */
    public function index(): Response
    {
        return $this->render('chauffeur/index.html.twig', [
            'controller_name' => 'ChauffeurController',
        ]);
    }
    /**
     * @Route("/afficheC", name="afficheC")
     */
    public function afficheC(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Chauffeur::class);
        //faire appel Ã  la fonction findAll()
        $chauffeur=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('chauffeur/afficheC.html.twig', [
            'c' => $chauffeur,
        ]);
    }
     /**
     * @Route("/frontC", name="frontC")
     */
    public function frontC(): Response
    {
        //rÃ©cupÃ©rer le repository pour utiliser la fonction findAll
        $r=$this->getDoctrine()->getRepository(Chauffeur::class);
        //faire appel Ã  la fonction findAll()
        $chauffeur=$r->findAll();

        //ou $r=$this->getDoctrine()->getRepository(Classroom::class)->findAll();
        return $this->render('chauffeur/frontC.html.twig', [
            'c' => $chauffeur,
        ]);
    }
     /**
     * @Route("/supp/{id}", name="suppC")
     */
    public function supp($id): Response

    {
        //récupérer le classroom à supprimer
        $chauffeur=$this->getDoctrine()->getRepository(Chauffeur::class)->find($id);
        //on passe à la suppression
        $em=$this->getDoctrine()->getManager();
        $em->remove($chauffeur);
        $em->flush();

        return $this->redirectToRoute('afficheC');
    }
     
    /**
     * @Route("/ajoutC", name="ajoutC")
     */
    public function ajoutC(Request $request): Response
    {
        //crÃ©ation du formulaire

        $c= new Chauffeur();
        $form = $this->createForm(ChauffeurType::class, $c);
        $form -> add ('Ajouter', SubmitType::Class);
        //rÃ©cupÃ©rer les donnÃ©es saisies depuis la requete http
        $form->handleRequest($request);
        if ($form->isSubmitted()&& $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($c);
            $em->flush();
            

            return $this->redirectToRoute('ajoutC');
        }


        return $this->render('chauffeur/ajoutC.html.twig', [
            'form' => $form->createView(),
        ]);

    }
    /**
     * @Route("/modifC/{id}", name="modifC")
     */
    public function modifP(Request $request,$id): Response
    {
        //rÃ©cupÃ©rer le classroom Ã  modifier
        $chauffeur=$this->getDoctrine()->getRepository(Chauffeur::class)->find($id);
        //crÃ©ation du formulaire rempli
        $form=$this->createForm(ChauffeurType::class,$chauffeur);
        //rÃ©cupÃ©rer les donnÃ©es saisies depuis la requete http
        $form->handleRequest($request);
        if($form->isSubmitted())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();
            return $this->redirectToRoute('afficheC');
        }

        return $this->render('chauffeur/modifC.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    
}
