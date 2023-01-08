<template>
  <v-container style="margin-top: 2%">
    <div style="margin-top: 50px">
      <v-text-field
          dense
          label="Поиск по космодестантинкам"
          v-model="name"
          name="name"
          type="text"
          color="#0E1117"
          outlined
          clearable
          :disabled="healthCheck"
          style="border-radius: 5px; width: 40%; display: inline-block"
      />
      <v-card outlined
              style="margin-left: 70px; display: inline-block;  position: absolute; left: 50%; top: 8.8%; width: 20%; height: 51px">

        <v-icon left style="margin-left: 10px;">
          {{ icons.mdiHeart }}
        </v-icon>
        <v-select
            v-model="healthType"
            solo
            dense
            :items="healthList"
            v-on:change="updateHealthType(healthType)"
            name="Health"
            color=#778899
            label="Здоровье"
            editable
            style="display: inline-block;width: 50%; margin-top: 6px;"
        ></v-select>
        <div style="display: inline-block; margin-left: 8px"/>


        <v-text-field
            dense
            label="Количество"
            v-model="health"
            name="Health"
            type="number"
            color="#0E1117"

            clearable
            style="border-radius: 5px; width: 31%; display: inline-block"
        />
      </v-card>
      <div style="padding: 10px; display: inline-block; margin-left: 34%"/>
      <v-btn color=#F16063 outlined @click="clear"
      >
        <v-icon left>
          {{ icons.mdiCloseCircleOutline }}
        </v-icon>
        Отчистить
      </v-btn>
      <div style="padding: 10px; display: inline-block"/>
      <v-btn color=#0E1117 outlined @click="search" :loading="loading"
      >
        <v-icon left>
          {{ icons.mdiMagnify }}
        </v-icon>
        Поиск
      </v-btn>
    </div>
    <div>
      <v-icon left style="display: inline-block">
        {{ icons.mdiHumanMale }}
      </v-icon>
      <v-select
          v-model="category"
          dense
          solo
          :items="categoryList"
          name="Category"
          color=#778899
          label="Категория"
          hint="Категория"
          editable
          style="display: inline-block; width: 14%"
          :disabled="healthCheck"
      ></v-select>
      <div style="padding: 10px; display: inline-block"/>
      <v-icon left style="display: inline-block">
        {{ icons.mdiPistol }}
      </v-icon>
      <v-select
          v-model="weaponType"
          dense
          solo
          :items="weaponTypeList"
          name="WeaponType"
          color=#778899
          label="Оружие"
          editable
          style="display: inline-block; width: 14%"
          :disabled="healthCheck"
      ></v-select>
      <div style="padding: 10px; display: inline-block"/>
      <v-icon left style="display: inline-block">
        {{ icons.mdiSwordCross }}
      </v-icon>
      <v-select
          v-model="meleeWeapon"
          dense
          solo
          :items="meleeWeaponList"
          name="MeleeWeapon"
          color=#778899
          label="Ближнее оружие"
          editable
          style="display: inline-block; width: 16%"
          :disabled="healthCheck"
      ></v-select>
      <div style="margin-left: 4%; display: inline-block"/>
      <div style="display: inline-block; width: 200px"
      >
      </div>
      <v-select
          :disabled="healthCheck"
          v-model="sortBy"
          name="sortBy"
          dense
          :item-text="'title'"
          :item-value="'name'"
          :items="sortByList"
          color=#778899
          label="Сортировка:"
          editable
          style="display: inline-block; width: 9.5%; margin-left: 6%"
      ></v-select>
      <v-switch
          color=#778899
          v-model="order"
          :label="`${order === true ? 'ASC' : 'DESC'}`"
          style="display: inline-block;"
      ></v-switch>
    </div>

    <v-divider style="margin-top: 5px"></v-divider>

    <div style="margin-left: 11px">
      <div style="margin-top: 30px; margin-bottom: 30px; font-size: 20px" class="font-weight-medium">
        Список космодесантников, отсортированных по полю "{{ sortBy }}"
      </div>
      <v-row v-for="(item, index) in this.$store.getters.allSpacemarines" :key="index">
        <SpacemarineButton :index="item.id" :SpacemarineName="item.name" :health="item.health"
                           :creation-date="item.creationDate" :starShipId="item.starShipId"
                           :category="item.category" :weapon-type="item.weaponType" :melee-weapon="item.meleeWeapon"
                           @updateParent="updateDialog"/>
      </v-row>
      <v-divider style="margin-top: 4%"></v-divider>
      <div style="margin-top: 2%"/>
    </div>
    <div class="text-center">
      <v-pagination
          color="#0E1117"
          v-model="page"
          :length=pageNumber
          :total-visible="5"
          @input="search"
      ></v-pagination>
    </div>

    <v-alert v-if="errorFlag" type="error" dismissible style="position: absolute; right: 2%; bottom: 2%">
      {{ errorMessage }}
    </v-alert>
  </v-container>
</template>

<script>
import axios from "axios";
import {mdiCloseCircleOutline, mdiHeart, mdiHumanMale, mdiMagnify, mdiPistol, mdiSwordCross} from "@mdi/js";
import SpacemarineButton from "@/components/mainPage/SpacemarineButton";

export default {
  name: "SearchComponent",
  components: {SpacemarineButton},

  data: () => ({
    icons: {
      mdiMagnify,
      mdiCloseCircleOutline,
      mdiHeart,
      mdiHumanMale,
      mdiSwordCross,
      mdiPistol
    },

    errorFlag: false,
    errorMessage: '',

    menu: false,
    loading: false,

    healthCheck: false,
    titleSortBy: '',

    Spacemarines: [],
    slotSize: 28,
    renderComponent: false,
    mapPath: '',

    name: null,
    health: null,
    category: null,
    weaponType: null,
    meleeWeapon: null,
    creationDate: null,
    sortBy: null,
    page: 1,
    pageNumber: '',
    limit: 4,
    order: true,

    healthType: '',
    categoryList: ["AGGRESSOR", "ASSAULT", "HELIX"],
    weaponTypeList: ["COMBI_FLAMER", "GRENADE_LAUNCHER", "INFERNO_PISTOL"],
    meleeWeaponList: ["CHAIN_SWORD", "CHAIN_AXE", "POWER_FIST"],
    sortByList: [{
      name: "name",
      title: "По имени"
    },
      {
        name: "health",
        title: "По здовроью"
      },
      {
        name: "category",
        title: "По категории"
      },
      {
        name: "weaponType",
        title: "По оружию"
      },
      {
        name: "meleeWeapon",
        title: "По оружию ближнего боя"
      }],

    healthList: ["HP равно: ", "HP больше чем: "],
  }),

  methods: {
    getListOfSpacemarines() {
      this.Spacemarines = new Array(this.limit)
      let str = "/spacemarines/search"
      let data = {
        name: this.name,
        health: this.health,
        category: this.category,
        weaponType: this.weaponType,
        meleeWeapon: this.meleeWeapon,
        //creationDate: this.creationDate,
        sortBy: this.sortBy,
        page: this.page,
        limit: this.limit,
        order: (this.order === true ? 'ASC' : 'DESC')
      }
      axios.create(this.getHeader(1))
          .post(str, data)
          .then(resp => {
            console.log(resp)
            this.Spacemarines = resp.data.items
            this.pageNumber = resp.data.pageNumber
            this.$store.commit('clearAllSpacemarines')
            this.$store.commit('updateSpacemarines', this.Spacemarines)
          })
          .catch(err => {
            this.showError(err.response.data.message)
          })
    },

    async search() {
      this.loading = true
      let oldHealthCheck = this.healthCheck
      this.healthCheck = true


      switch (this.healthType) {
        case this.healthList[0]:
          this.getListOfSpacemarines()
          break;
        case this.healthList[1]: {
          this.Spacemarines = new Array(this.limit)
          let str = "spacemarines/search/health/greater"
          let data = {
            health: this.health,
            page: this.page,
            limit: this.limit
          }
          axios.create(this.getHeader(1))
              .post(str, data)
              .then(resp => {
                this.Spacemarines = resp.data.items
                this.$store.commit('clearAllSpacemarines')
                this.$store.commit('updateSpacemarines', this.Spacemarines)
              }).catch(() => {
            this.renderComponent = false
          })
          break;
        }
      }

      await new Promise(resolve => setTimeout(resolve, this.awaitTimer))

      this.loading = false
      this.healthCheck = oldHealthCheck
    },

    updateHealthType: function (healthType) {
      this.healthCheck = healthType !== this.healthList[0];
    },

    updateDialog() {
      this.renderComponent = true
      this.getListOfSpacemarines()
    },

    clear() {
      this.name = null
      this.health = null
      this.category = null
      this.weaponType = null
      this.meleeWeapon = null
      this.creationDate = null
      this.healthType = this.healthList[0]
      this.healthCheck = false
      this.titleSortBy = this.sortByList[0]
      this.sortBy = this.sortByList[0].name
    },

    async showError(errorMessage) {
      if (this.errorFlag === true) {
        this.errorMessage = ''
        this.errorFlag = false
        await new Promise(resolve => setTimeout(resolve, 200))
      }
      this.errorMessage = errorMessage
      this.errorFlag = true
      await new Promise(resolve => setTimeout(resolve, this.errorTimer))
      this.errorMessage = ""
      this.errorFlag = false
    }
  },
  created() {
    this.$root.$refs.SearchComp = this;
    this.pageNumber = 10
    this.healthType = this.healthList[0]
    this.sortBy = this.sortByList[0].name
    this.getListOfSpacemarines()
  },
}
</script>

<style scoped>

</style>