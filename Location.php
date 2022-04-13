<?php

namespace App\Entity;

use App\Repository\LocationRepository;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=LocationRepository::class)
 */
class Location
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     *   @Assert\NotBlank(message="Le Champ Nom est obligatoire")
     * @Assert\Length(
     *     min=5,
     *     max=50,
     *     minMessage="Le titre doit contenir au moins 5 carcatères ",
     *     maxMessage="Le titre doit contenir au plus 20 carcatères"
     * )
     */
    private $model;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotNull(message="Le Champ Titre est obligatoire")
     */
    private $prix;

    /**
     * @ORM\Column(type="string", length=255)
     *   @Assert\NotBlank(message="Le Champ Nom est obligatoire")
     * @Assert\Length(
     *     min=5,
     *     max=50,
     *     minMessage="Le titre doit contenir au moins 5 carcatères ",
     *     maxMessage="Le titre doit contenir au plus 20 carcatères"
     * )
     */
    private $dateloc;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotNull(message="Le Champ Titre est obligatoire")
     */
    private $duree;

    /**
     * @ORM\ManyToOne(targetEntity=Chauffeur::class, inversedBy="location")
     */
    private $chauffeur;
    

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getModel(): ?string
    {
        return $this->model;
    }

    public function setModel(string $model): self
    {
        $this->model = $model;

        return $this;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function setPrix(int $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getDateloc(): ?string
    {
        return $this->dateloc;
    }

    public function setDateloc(string $dateloc): self
    {
        $this->dateloc = $dateloc;

        return $this;
    }

    public function getDuree(): ?int
    {
        return $this->duree;
    }

    public function setDuree(int $duree): self
    {
        $this->duree = $duree;

        return $this;
    }

    public function getChauffeur(): ?Chauffeur
    {
        return $this->chauffeur;
    }

    public function setChauffeur(?Chauffeur $chauffeur): self
    {
        $this->chauffeur = $chauffeur;

        return $this;
    }
}
