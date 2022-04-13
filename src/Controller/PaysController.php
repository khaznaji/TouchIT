<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Entity\Pays;
use App\Repository\PaysRepository;
use App\Form\PaysType;
use Symfony\Component\HttpFoundation\Request;
use Knp\Component\Pager\PaginatorInterface;


class PaysController extends AbstractController
{
    /**
     * @Route("/pays", name="app_pays") 
     */
    public function index(): Response
    {
        return $this->render('pays/index.html.twig', [
            'controller_name' => 'PaysController',
        ]);
    }
         /**
  * @Route("/newPays", name="newPays")
   
  */

    public function newPays(Request $request )
    {   $pays= new Pays();
        $form =$this->createForm (PaysType::class  , $pays);
        $form -> add ('Ajouter', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()){
            $pays= $form->getData();
            $em= $this->getDoctrine()->getManager();
            $em->persist ($pays);
            $em->flush();
            return $this->redirectToRoute('newPays');
        }
        return $this->render('pays/index.html.twig', [
            'form' => $form -> createView (),
        ]);
    }

    /**
     * @Route("/AffichePays", name="AffichePays")
     */
    public function AffichePays(){
        $repository=$this->getDoctrine()->getRepository(Pays::class); 
        $pays=$repository->findAll();
        return $this->render('pays/Affiche.html.twig', 
        ['aa'=>$pays]); 
     }
     
        /**
         * @Route ("/deletePays/{id}",name="deletePays")
         */
    public function deletePays($id)
    {
        $em=$this->getDoctrine()->getManager();
        $pays= $em ->getRepository (Pays::class)->find ($id);
        $em->remove($pays);
        $em->flush();
         return $this->redirectToRoute('AffichePays') ;
    }
      /**
     * @Route("/updatePays/{id}", name="updatePays")
     */
    public function updatePays(Request $request, $id)
    {
        $em= $this->getDoctrine()->getManager();
        $pays= $em ->getRepository (Pays::class)->find ($id);
        $form =$this->createForm (PaysType::class, $pays);
        $form -> add ('Update/Modifier', SubmitType::Class);
        $form ->handleRequest($request);
        if ($form->isSubmitted() && $form-> isValid ())
        {
            $em->flush();
            return $this->redirectToRoute('AffichePays');
        }
        return $this->render('pays/Modifier.html.twig', [
            'form_title'=> "modifier un Event",
            'w' => $form -> createView (),
        ]);
    }

}
