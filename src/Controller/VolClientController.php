<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\VolClient;
use App\Repository\VolClientRepository;
use App\Form\VolClientType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
class VolClientController extends AbstractController
{
    /**
     * @Route("/vol/client", name="app_vol_client")
     */
    public function index(): Response
    {
        return $this->render('vol_client/index.html.twig', [
            'controller_name' => 'VolClientController',
        ]);
    }
     /**
     * @Route("/AfficheVolClient", name="AfficheVolClient")
     */
    public function AfficheVolClient(){
        $repository=$this->getDoctrine()->getRepository(VolClient::class); 
        $volClient=$repository->findAll();
        return $this->render('vol_client/Affiche.html.twig', 
        ['aa'=>$volClient]); 
     }
       /**
     * @Route("/AfficheVolClientFront", name="AfficheVolClientFront")
     */
    public function AfficheVolClientFront(){
        $repository=$this->getDoctrine()->getRepository(VolClient::class); 
        $volClient=$repository->findAll();
        return $this->render('vol_client/AfficheFront.html.twig', 
        ['aa'=>$volClient]); 
     }
      /**
  * @Route("/newVolClient", name="newVolClient")
   
  */

  public function newVolClient(Request $request )
  {   $volClient= new VolClient();
      $form =$this->createForm (VolClientType::class  , $volClient);
      $form -> add ('Ajouter', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted()&& $form-> isValid ()){
          $volClient= $form->getData();
          $em= $this->getDoctrine()->getManager();
          $em->persist ($volClient);
          $em->flush();
          return $this->redirectToRoute('AfficheVolClientFront');
      }
      return $this->render('vol_client/index.html.twig', [
          'form' => $form -> createView (),
      ]);
  }
   /**
         * @Route ("/deleteVolClient/{id}",name="deleteVolClient")
         */
        public function deleteVolClient($id)
        {
            $em=$this->getDoctrine()->getManager();
            $volClient= $em ->getRepository (VolClient::class)->find ($id);
            $em->remove($volClient);
            $em->flush();
             return $this->redirectToRoute('AfficheVolClient') ;
        }
         /**
         * @Route ("/deleteVolClientFront/{id}",name="deleteVolClientFront")
         */
        public function deleteVolClientFront($id)
        {
            $em=$this->getDoctrine()->getManager();
            $volClient= $em ->getRepository (VolClient::class)->find ($id);
            $em->remove($volClient);
            $em->flush();
             return $this->redirectToRoute('AfficheVolClientFront') ;
        }
            /**
   * @Route("/updateVolClient/{id}", name="updateVolClient")
   */
  public function updateVolClient(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $volClient= $em ->getRepository (VolClient::class)->find ($id);
      $form =$this->createForm (VolClientType::class, $volClient);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form-> isValid ())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheVolClient');
      }
      return $this->render('vol_client/Modifier.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
  /**
   * @Route("/updateVolClientFront/{id}", name="updateVolClientFront")
   */
  public function updateVolClientFront(Request $request, $id)
  {
      $em= $this->getDoctrine()->getManager();
      $volClient= $em ->getRepository (VolClient::class)->find ($id);
      $form =$this->createForm (VolClientType::class, $volClient);
      $form -> add ('Update/Modifier', SubmitType::Class);
      $form ->handleRequest($request);
      if ($form->isSubmitted() && $form-> isValid ())
      {
          $em->flush();
          return $this->redirectToRoute('AfficheVolClientFront');
      }
      return $this->render('vol_client/ModifierFront.html.twig', [
          'form_title'=> "modifier un Event",
          'w' => $form -> createView (),
      ]);
  }
}
