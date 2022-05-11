<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220427112519 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE chauffeur (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, sexe VARCHAR(255) NOT NULL, num INT NOT NULL, disponibilite VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE location (id INT AUTO_INCREMENT NOT NULL, nom_id INT DEFAULT NULL, model VARCHAR(255) NOT NULL, prix INT NOT NULL, dateloc DATE NOT NULL, duree INT NOT NULL, INDEX IDX_5E9E89CBC8121CE9 (nom_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE locationc (id INT AUTO_INCREMENT NOT NULL, chauffeur_id INT DEFAULT NULL, model VARCHAR(255) NOT NULL, dateloc DATE NOT NULL, duree INT NOT NULL, INDEX IDX_A515ADE85C0B3BE (chauffeur_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE location ADD CONSTRAINT FK_5E9E89CBC8121CE9 FOREIGN KEY (nom_id) REFERENCES chauffeur (id)');
        $this->addSql('ALTER TABLE locationc ADD CONSTRAINT FK_A515ADE85C0B3BE FOREIGN KEY (chauffeur_id) REFERENCES chauffeur (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE location DROP FOREIGN KEY FK_5E9E89CBC8121CE9');
        $this->addSql('ALTER TABLE locationc DROP FOREIGN KEY FK_A515ADE85C0B3BE');
        $this->addSql('DROP TABLE chauffeur');
        $this->addSql('DROP TABLE location');
        $this->addSql('DROP TABLE locationc');
    }
}
